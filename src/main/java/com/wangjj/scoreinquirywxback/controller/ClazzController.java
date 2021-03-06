package com.wangjj.scoreinquirywxback.controller;

import com.wangjj.scoreinquirywxback.entity.Clazz;
import com.wangjj.scoreinquirywxback.entity.Grade;
import com.wangjj.scoreinquirywxback.service.ClazzService;
import com.wangjj.scoreinquirywxback.vo.request.PageParameter;
import com.wangjj.scoreinquirywxback.vo.response.APIResultBean;
import com.wangjj.scoreinquirywxback.vo.response.Layui;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName : ClazzController
 * @Author : wangJJ
 * @Date : 2020/1/1 16:13
 * @Description : 班级请求处理类
 */
@Api(description = "班级类")
@RestController
@RequestMapping("/clazz")
public class ClazzController {

	@Autowired
	private ClazzService clazzService;

	@GetMapping("/page")
	@ApiOperation(value = "获得分页后班级页列表", notes = "分页获得班级列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "分页参数:页码(从1开始)",defaultValue = "1"),
			@ApiImplicitParam(name = "limit", value = "分页参数:页大小", defaultValue = "10"),
			@ApiImplicitParam(name = "gradeId", value = "年级Id", defaultValue = "2019")
	})
	public APIResultBean clazzPage(@RequestParam Integer page,
								   @RequestParam Integer limit,
								   @RequestParam(required = false) Long gradeId) {
		System.out.println(page + " " + limit);
		PageRequest pageRequest = PageRequest.of(page - 1,limit);
		Page<Clazz> clazzPage = clazzService.getClazzListByPage(Clazz.builder()
				.gradeId(gradeId)
				.build(),
				pageRequest);
		return APIResultBean.ok(clazzPage).build();
	}

	@GetMapping("/list")
	@ApiOperation(value = "获得班级列表", notes = "根据年级Id获得班级列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "gradeId", value = "年级Id", defaultValue = "2019")
	})
	public APIResultBean clazzList(@RequestParam Long gradeId) {

		List<Clazz> clazzList = clazzService.getClazzList(Clazz.builder()
						.gradeId(gradeId)
						.build());
		return APIResultBean.ok(clazzList).build();
	}

	@PostMapping
	@ApiOperation(value = "增加班级")
	@ApiImplicitParam(name = "clazz", value = "班级信息", dataType = "Clazz")
	public APIResultBean addClazz(@RequestBody Clazz clazz) {
		clazzService.addClazz(clazz);
		return APIResultBean.ok("添加成功").build();
	}

	@DeleteMapping
	@ApiOperation(value = "删除班级")
	@ApiImplicitParam(name = "clazzId", value = "班级id", dataType = "Long")
	public APIResultBean deleteClazz(@RequestParam Long clazzId) {
		clazzService.deleteClazz(clazzId);
		return APIResultBean.ok("删除成功").build();
	}
	/*@GetMapping
	@ApiOperation(value = "根据年级id查询班级信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "gradeId",value = "年级Id",dataType = "Long"),
	})
	public APIResultBean findClazz(@RequestParam Long gradeId) {

		List<Clazz> clazzList = clazzService.getClazzList(Clazz.builder().gradeId(gradeId).build());
		return APIResultBean.ok(clazzList).build();
	}*/

}
