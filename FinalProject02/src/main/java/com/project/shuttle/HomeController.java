package com.project.shuttle;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.project.shuttle.model.biz.TBUserBiz;
import com.project.shuttle.model.dto.TBUserDto;

@Controller
public class HomeController {
	
	@Autowired
	private TBUserBiz biz;
	
	@RequestMapping("/")
	public ModelAndView ScheduleManagementRoot() {
		ModelAndView mav = new ModelAndView("redirect:main.do");
		return mav;
	}
	
	@RequestMapping(value = "/main.do")
	public ModelAndView Main(ModelAndView mav) {
		mav.setViewName("main");
		return mav;
	}
	
	@RequestMapping("/signUp.do")
	public ModelAndView SignUp(ModelAndView mav) {
		mav.setViewName("user_registration");
		return mav;
	}
	
	@RequestMapping(value = "/jusoPopup.do")
	public ModelAndView jusoPopup(ModelAndView mav) {
		mav.setViewName("jusoPopup");
		return mav;
	}
	
	@RequestMapping(value = "/logins.do", method = RequestMethod.POST)
	public void logins() {
		
	}
	
	@RequestMapping("/logout.do")
	public RedirectView logout(ModelAndView mav, HttpSession session) {
//		session.invalidate();
		// 세션에 존재하는 모든 정보 삭제
		session.removeAttribute("loginInfo");
		// 원하는 세션 정보만 삭제
		RedirectView review = new RedirectView("/main.do");
		// redirectView 타입이 있길래 써봄
		
		return review;
	}
	
	@RequestMapping(value = "/loginajax.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> loginAjax(HttpSession session, @RequestBody TBUserDto dto){
		/*
		 * @RequestBody : request객체를 통해 넘어오는 데이터를 java 객체에 binding
		 * @ResponseBody : java 객체를 response 객체에 binding해서 보내줌
		 */
		
		/*
		 * 1. 최초 json형태의 값이 들어오면 이를 jackson이 json형태로 변환해주고
		 * 이를 @RequestBody가 받아서 작업을 해줌
		 * 작업은 key:value중 key를 읽어서 해당 dto에 있는 setter를 찾아서 value를 setter해주는 것
		 * 
		 * 이 일련의 과정을 어노테이션만 쓰면 알아서 해줌
		 * 
		 * {"memberid":"김진성","memberpw":"1234"}
		 * @RequestBody MemberDto dto
		 * dto에는 밑의 setter가 존재함
		 * setMemberid(String id)
		 * setMemberpw(String pw)
		 * 
		 * dto.setMemberid("김진성");
		 * dto.setMemberpw("1234");
		 */
		System.out.println(dto.getUserId()+"//"+dto.getUserPw()+" //testController");
		
		
		TBUserDto res = biz.login(dto);
		boolean check = false;
		if(res!=null) {//로그인 정보가 있다면
			session.setAttribute("loginInfo", res);
			check = true;
		}
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("check", check);
		System.out.println(check+"check상태");
		return map;
	}
	
	

}
