package com.yr.homework.model.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Board {
	private int boardNo;
	private String nickName;
	private String boardTitle;
	private String content;
	private String writeDate;
	private boolean important;
	
	
	public boolean isImportant() {
		return important;
	}


	public void setImportant(boolean important) {
		this.important = important;
	}
	


	public String getBoardTitle() {
		return boardTitle;
	}


	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}


	public Board(int boardNo, String nickName,String boardTitle, String content, String writeDate,boolean important) {
		super();
		this.boardNo = boardNo;
		this.nickName = nickName;
		this.content = content;
		this.writeDate = writeDate;
		this.important=important;
		this.boardTitle=boardTitle;
	}

	
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}


	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", nickName=" + nickName + ", boardTitle=" + boardTitle + ", content="
				+ content + ", writeDate=" + writeDate + ", important=" + important + "]";
	}
	
	
	

}
