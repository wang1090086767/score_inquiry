package com.wangjj.scoreinquirywxback.service.impl;

import com.wangjj.scoreinquirywxback.pojo.dto.GradeDTO;
import com.wangjj.scoreinquirywxback.pojo.entity.Grade;
import com.wangjj.scoreinquirywxback.service.GradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GradeServiceImplTest {

	@Autowired
	private GradeService gradeService;

	@Test
	void addGrade() {

		GradeDTO gradeDTO = new GradeDTO();
		gradeDTO.setId(2019L);
		gradeDTO.setCourseIds("25");
		gradeService.saveGrade(gradeDTO);
	}

	@Test
	void isExist() {
	}

	@Test
	void findByGradeName() {
	}

	@Test
	void deleteGradeById() {
        gradeService.deleteGradeById(2019L);
	}

	@Test
	void findGradeList() {
	}

	@Test
	void findGradePage() {
	}

	@Test
	void addGradeCourse() {
	}
}