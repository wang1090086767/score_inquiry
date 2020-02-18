package com.wangjj.scoreinquirywxback.service;

import com.wangjj.scoreinquirywxback.dao.ClazzRepository;
import com.wangjj.scoreinquirywxback.dao.GradeRepository;
import com.wangjj.scoreinquirywxback.dao.TeacherRepository;
import com.wangjj.scoreinquirywxback.exception.GlobalException;
import com.wangjj.scoreinquirywxback.pojo.dto.ClazzDTO;
import com.wangjj.scoreinquirywxback.pojo.dto.response.PageResult;
import com.wangjj.scoreinquirywxback.pojo.entity.Clazz;
import com.wangjj.scoreinquirywxback.pojo.entity.Grade;
import com.wangjj.scoreinquirywxback.pojo.entity.Teacher;
import com.wangjj.scoreinquirywxback.util.ParameterUtils;
import com.wangjj.scoreinquirywxback.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName : ClazzService
 * @Author : 1090086767
 * @Date : 2019/12/25 17:28
 * @Description : 班级的业务
 */
@Service
public class ClazzService {

	@Autowired
	private ClazzRepository clazzRepository;
	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	public List<ClazzDTO> getClazzList(ClazzDTO clazzDTO) {
		List<Clazz> clazzes = clazzRepository.findAll(getClazzSpecification(clazzDTO));

		List<ClazzDTO> convert = PropertyUtils.convert(clazzes, u -> {
			ClazzDTO dto = getClazzDTO(u);
			return dto;
		});

		return convert;
	}

	private Specification<Clazz> getClazzSpecification(ClazzDTO clazz) {
		return (Specification<Clazz>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<>();

			if(Objects.nonNull(clazz.getId())) {
				predicateList.add(criteriaBuilder.equal(root.get("id"),clazz.getId()));
			}


			if (Objects.nonNull(clazz.getGradeId())) {
				predicateList.add(criteriaBuilder.equal(root.get("grade").get("id"), clazz.getGradeId()));
			}

			if(Objects.nonNull(clazz.getTeacherId())) {
				predicateList.add(criteriaBuilder.equal(root.joinSet("teachers").get("id"),clazz.getTeacherId()));
			}

			if (predicateList != null) {
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
			}
			return null;
		};
	}

	public PageResult<ClazzDTO> getClazzListByPage(ClazzDTO clazzDTO, Pageable pageable) {
		Page<Clazz> clazzPage = clazzRepository.findAll(getClazzSpecification(clazzDTO), pageable);
		PageResult<ClazzDTO> convert = PropertyUtils.convert(clazzPage, u -> {
			ClazzDTO dto = getClazzDTO(u);
			return dto;
		});
		return convert;
	}

	/**
	 * 转化成数据传输对象
	 * @param u
	 * @return
	 */
	private ClazzDTO getClazzDTO(Clazz u) {
		ClazzDTO dto = new ClazzDTO();
		PropertyUtils.copyNoNullProperties(u, dto);
		dto.setGradeId(u.getGrade().getId());
		return dto;
	}


	public boolean isExist(Clazz clazz) {
	/*	if(!gradeRepository.existsById(clazz.getGradeId())) {
			throw new GlobalException("年级不存在");
		}
		Clazz c = clazzRepository.findByGradeIdAndClazzName(clazz.getGradeId(), clazz.getClazzName());*/
		return Objects.nonNull(null);
	}

	@Transactional
	public void deleteClazz(String ids) {
		List<Long> clazzIds = ParameterUtils.analyse(ids);
		List<Clazz> clazzes = clazzRepository.findAllById(clazzIds);
		clazzes.forEach(e->{
			e.getTeachers().forEach(t->{
				t.getClazzSet().remove(e);
			});
		});
		clazzRepository.deleteByIdIn(clazzIds);
	}



	public Clazz findById(Long id) {
		return clazzRepository.getOne(id);
	}



	public void saveClazz(ClazzDTO clazzDTO) {
		if(gradeRepository.existsById(clazzDTO.getGradeId())) {
			throw new GlobalException(String.format("不存在gradeId为%s的年级",clazzDTO.getGradeId()));
		}

		Grade grade = gradeRepository.getOne(clazzDTO.getGradeId());
		Clazz clazz = clazzRepository.existsById(clazzDTO.getId())
				?clazzRepository.getOne(clazzDTO.getId())
				:new Clazz();

		PropertyUtils.copyNoNullProperties(clazzDTO,clazz);
		clazz.setGrade(grade);
		clazzRepository.save(clazz);
	}

	@Transactional
	public void saveClazzTeacher(Long clazzId, Long teacherId){
		if(!clazzRepository.existsById(clazzId)) {
			throw new GlobalException("班级不存在");
		}
		if(!teacherRepository.existsById(teacherId)) {
			throw new GlobalException("老师不存在");
		}
		Clazz clazz = clazzRepository.getOne(clazzId);
		Teacher teacher = teacherRepository.getOne(teacherId);
		clazz.getTeachers().add(teacher);
	}
}
