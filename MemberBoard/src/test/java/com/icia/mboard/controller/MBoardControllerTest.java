package com.icia.mboard.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.icia.mboard.controller.MBoardControllerTest;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@Slf4j
public class MBoardControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mock;
	
	@Before
	public void setup() {
		System.out.println("setup 메소드 호출");
		this.mock = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void boardWriteTest() throws Exception {
		System.out.println("글쓰기 테스트 호출");
		log.info("글쓰기 테스트");
		log.info(mock.perform(MockMvcRequestBuilders.post("/boardwrite")
				.param("bwriter", "testwriter")
				.param("btitle", "testtitle")
				.param("bcontents", "testcontents"))
				.andReturn().getModelAndView().getViewName());
		System.out.println("글쓰기 테스트 호출 종료");
	}
	
	
	
	
	
	
	
	
	
	
	
}
