package com.yr.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yr.homework.model.vo.Board;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BoardController {
	Enumeration<?> iter=getBoardProperties();
	
	Properties prop=new Properties();
	String filePath=BoardController.class.getResource("/board.properties").getPath();
	
	public Enumeration<?> getBoardProperties(){
		Enumeration<?> iter=null;
		try {
			prop.load(new BufferedReader(new FileReader(filePath)));		
			iter=prop.propertyNames();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return iter;
	}
	
	
	

	@RequestMapping("/index")
	public String getBoardListToIndex(Model model,HttpServletRequest request) {
		//보트 리스트 가져오기
		Enumeration<?> boardList=getBoardProperties();
		ArrayList<Board> list=new ArrayList<Board>();
		if(boardList!=null) {
		while(boardList.hasMoreElements()) {
			String boardNo=(String) boardList.nextElement();
			String[] board=prop.getProperty(boardNo).split("','");
			Board b=new Board(Integer.parseInt(board[0]), board[1], board[2], board[3], board[4],Boolean.parseBoolean(board[5]));
			list.add(b);
		}
		model.addAttribute("list", list);
		}
		model.addAttribute("msg", "stop");
		return "board/boardList";
		
	}
	
	
	@RequestMapping("/boardView.do")
	public ModelAndView boardView(ModelAndView mav,@RequestParam String boardNo,HttpServletRequest request) {
		//보드 번호로 게시글 확인
		Enumeration<?> iter=getBoardProperties();
		String nickName2=null;
		
		while(iter.hasMoreElements()) {
			if(iter.nextElement().equals(boardNo)) {
				String[] board=prop.getProperty(boardNo).split("','");
				Board b=new Board(Integer.parseInt(board[0]), board[1], board[2], board[3], board[4],Boolean.parseBoolean(board[5]));
				mav.addObject("board",b);
			}
		}
		//접속자 확인을 위한 쿠키 가져오기
		Cookie[] cookies=request.getCookies();
		for(Cookie c:cookies) {
			//쿠키가 있다면 닉네임 값을 넣고
			if(c.getName().equals("cookie")) {
				nickName2=c.getValue();
				break;
			}
			//아니여도 비교를 위해 넣어준다.
			else {
				nickName2=c.getValue();
			}
		}
		
		mav.addObject("nickName2",nickName2);
		mav.setViewName("board/boardView");
		return mav;
	}
	
	@RequestMapping("/writeBoard.do")
	public ModelAndView writeBoard(HttpServletRequest request,ModelAndView mav,HttpServletResponse response) {
		Cookie[] cookies=request.getCookies();
		String nickName=null;
		
		if(cookies==null) {
		}else {
			for(Cookie c:cookies) {
				if(c.getName().equals("cookie")) {
				nickName=c.getValue();
				}
			}

			mav.addObject("nickName",nickName);
			mav.setViewName("board/writeBoard");
		}
		return mav;
	}
	
	@RequestMapping(value="/writeBoardEnd.do",method=RequestMethod.POST)
	public String writeBoardEnd(HttpServletResponse response,HttpServletRequest request,@RequestParam String boardTitle,@RequestParam String important,@RequestParam String nickName,@RequestParam String content,@RequestParam String date,ModelAndView mav) throws UnsupportedEncodingException {
		//중요 게시물 체크
		boolean bool=false;
			if(important.equals("true")) {
				bool=true;
			}else {
				bool=false;
			}
		String writeDate=null;
		try {
			Date transDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(date);
			SimpleDateFormat fom=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			writeDate=fom.format(transDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//쿠키가 없었다면 생성, 쿠키에 닉네임값 넣기
		Cookie[] cookies=request.getCookies();
		for(Cookie c:cookies) {
			if(!c.getName().equals("cookie")) {
				Cookie cookie=new Cookie("cookie", nickName);
				response.addCookie(cookie);
			}
		}
		
	
		
		//boardNo 생성
		//시퀀스가 없으므로 난수를 생성해서 게시글 번호로 등록
		Random random=new Random();
		Enumeration<?> iter=getBoardProperties();
		int boardNo=random.nextInt(99000);
		if(iter!=null) {
			while(iter.hasMoreElements()) {
				String propBoardNo=(String) iter.nextElement();
				//난수 boardNo과 properties의 key값이 같다면 난수 다시 생성.
				//element가 다  돌때까지 중복되는 수가 없다면 해당 boardNo을 key값으로 사용.
				if(boardNo==Integer.parseInt(propBoardNo)) {
					boardNo=random.nextInt(99000);
				}
			}
		}
		//게시글 properties에 등록하기
		String board=String.valueOf(boardNo)+"','"+nickName+"','"+boardTitle+"','"+content+"','"+writeDate+"','"+bool;		
		prop.setProperty(String.valueOf(boardNo), board);
			try {
				prop.store(new FileWriter(filePath), String.valueOf(boardNo));
			} catch (IOException e) {
			}
		
		return "redirect:/";
	}
	
	
	@RequestMapping("updateBoard.do")
	public String updateBoard(@RequestParam String boardNo,@RequestParam String boardTitle,@RequestParam String important,@RequestParam String nickName,@RequestParam String content,@RequestParam String date) {
		//중요 게시물 체크
		boolean bool=false;
			if(important.equals("true")) {
				bool=true;
			}else {
				bool=false;
			}
			
		String writeDate=null;
		try {
			Date transDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(date);
			SimpleDateFormat fom=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			writeDate=fom.format(transDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
				
		String board=boardNo+"','"+nickName+"','"+boardTitle+"','"+content+"','"+writeDate+"','"+bool;
		//수정 내용 덮어씌우기
		prop.setProperty(boardNo, board);
			try {
				prop.store(new FileWriter(filePath), boardNo);
			} catch (IOException e) {
			}
			
		return "redirect:/";
	}
	
	
	@RequestMapping("deleteBoard.do")
	public String deleteBoard(@RequestParam String boardNo) {
		Enumeration<?>iter=getBoardProperties();
		while(iter.hasMoreElements()) {
			if(iter.nextElement().equals(boardNo)) {
				prop.remove(boardNo);
				try {
					prop.store(new FileWriter(filePath), boardNo);
				} catch (IOException e) {
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value="searchKeyword.do",method=RequestMethod.POST)
	public ModelAndView searchKeyword(ModelAndView mav,@RequestParam String searchType,@RequestParam String searchKeyword) {
		ArrayList<Board>list=new ArrayList<Board>();
		Enumeration<?>iter=getBoardProperties();
		while(iter.hasMoreElements()) {
			if(searchType.equals("nickName")) {
				String boardNo=(String)iter.nextElement();
				String[] board=prop.getProperty(boardNo).split("','");
				if(searchKeyword.equals(board[1])) {
					Board b=new Board(Integer.parseInt(board[0]), board[1], board[2], board[3], board[4],Boolean.parseBoolean(board[5]));
					list.add(b);
				}
			}else if(searchType.equals("title")) {
				String boardNo=(String)iter.nextElement();
				String[] board=prop.getProperty(boardNo).split("','");
				if(searchKeyword.equals(board[2])) {
					Board b=new Board(Integer.parseInt(board[0]), board[1], board[2], board[3], board[4],Boolean.parseBoolean(board[5]));
					list.add(b);
				}
			}
		}
		mav.addObject("list",list);
		mav.setViewName("board/boardList");
		return mav;
	}

}
