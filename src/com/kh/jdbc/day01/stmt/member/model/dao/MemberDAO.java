package com.kh.jdbc.day01.stmt.member.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.stmt.member.model.vo.Member;



public class MemberDAO {
	// JDBC를 이용하여
	// Oracle DB에 접속하는 클래스
	// JDBC 코딩이 있어야 함.
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USERNAME = "TWJDBC";
	private final String USERPW = "TWJDBC";
	
	
	public void selectOne() {
		
	}
	
	public List<Member> selectList() {
		/*
		 * 1. 드라이버 등록
		 * 2. DBMS 연결 생성
		 * 3. Statement 생성
		 * 4. 쿼리문 전송
		 * 5. 결과값 받기
		 * 6. 자원해제
		 * 
		 */
		// 1. 왜 mList에 member가 들어가나요?
		// 2. rset은 왜 mList에 못들어가나요?
		// 3. rset을 member로 어떻게 바꾸나요?
		// 3.1 Member 클래스에는 필드와 게터/세터 필요
		// 3.2 ResultSet의 getString, getInt, getDate() 필요
		List<Member> mList = new ArrayList<Member>();
		try {
			//1
			Class.forName(DRIVER_NAME);
			//2
			Connection conn
				= DriverManager.getConnection(URL, USERNAME, USERPW);
			//3
			Statement stmt = conn.createStatement();
			//4
			String query ="SELECT * FROM MEMBER_TBL";
			ResultSet rset = stmt.executeQuery(query);
			//후처리
			while(rset.next()) {
				Member member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setMemberPw(rset.getString("MEMBER_PW"));
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setGender(rset.getString("GENDER"));
				member.setAge(rset.getInt("AGE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setRegDate(rset.getDate("REG_DATE"));
				mList.add(member);
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mList;
		
		
	}
	public void insertMember(Member member) {
		/*
		 *  1. 드라이버 등록
		 *  2. DBMS 연결생성
		 *  3. Statement 생성
		 *  4. 쿼리문 전송
		 *  5. 결과값 받기
		 *  6. 자원해제
		 */
		
		try {
			//1. 드라이버 등록(항상 같음)
			Class.forName(DRIVER_NAME);
			//2. DBMS 연결생성 & 접속정보 입력
			Connection conn = DriverManager.getConnection(URL, USERNAME, USERPW);
			//3. Statement 생성
			Statement stmt = conn.createStatement();
			//4. 쿼리문 전송 // 5.
			String query = "INSERT INTO MEMBER_TBL VALUES('"
			+member.getMemberId()+"', '"
			+member.getMemberPw()+"', '"
			+member.getMemberName()+"', '"
			+member.getGender()+"', "
			+member.getAge()+", '"
			+member.getEmail()+"','"
			+member.getPhone()+"','"
			+member.getAddress()+"','"
			+member.getHobby()+"', DEFAULT)";
			// ResultSEt rset = stmt.executeQuery(query); // SELECT 할때만 ResultSet은 Select의 결과
			int result = stmt.executeUpdate(query); // DML의 경우 호출하는 메소드
			//후처리
			if(result > 0 ) {
				//성공메세지 출력
				System.out.println("데이터 등록 성공");
				// commit;
			} else {
				//실패메세지 출력
				System.out.println("데이터 등록 실패");
				// rollback;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Member selectOne(String memberId) {
		Member member = null;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn
				= DriverManager.getConnection(URL, USERNAME, USERPW);
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = " + "'"+memberId+"'";
			ResultSet rset = stmt.executeQuery(query);
			if(rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				String memberPw= rset.getString("MEMBER_PW");
				String memberName = rset.getString("MEMBER_NAME");
				member.setMemberId(memberId);
				member.setMemberPw(memberPw);
				member.setMemberName(memberName);
				member.setGender(rset.getString("GENDER"));
				member.setAge(rset.getInt("AGE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setRegDate(rset.getDate("REG_DATE"));
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return member;
	}
}
