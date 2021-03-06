package com.wangjj.scoreinquirywxback;

import com.wangjj.scoreinquirywxback.dao.ParentRepository;
import com.wangjj.scoreinquirywxback.entity.Parent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
class ExamScoreInquiryWxBackApplicationTests {

	@Autowired
	private ParentRepository parentRepository;
	@Transactional
	@Test
	void contextLoads() {
		List<Parent> all = parentRepository.findAll();
		all.forEach(e -> {
		//	System.out.println(e.getChildren());
		});
	}

}
