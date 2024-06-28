package com.kh.jdbc.day02.stmt.member.model.dao;

import java.sql.*;
import java.util.*;

import com.kh.jdbc.day02.stmt.member.model.vo.Member;

public class MemberDAO {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USERNAME = "TWJDBC";
	private final String PASSWORD = "TWJDBC";
	
	//1 . 회원가입
	public void insertMember(Member member) {
		// JDBC 코딩절차
		// JDBC를 통해서 DB의 데이터를 가져옴
		/*
		 * 1. 드라이버 등록
		 * 2. 연결생성
		 * 3. Statement 생성
		 * 4. SQL문 전송
		 * 5. 결과받기
		 */
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(DRIVER_NAME);
			conn =
			DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.createStatement(); // 워크시트 열기
			// 쿼리문작성, ; 오타조심!, '홑따옴표 조심!
			String query = "INSERT INTO MEMBER_TBL VALUES('"+member.getMemberId()+"','"+member.getMemberPw()+"','"+member.getMemberName()+"','"+member.getGender()+"',"+member.getAge()+",'"
			+member.getEmail()+"','"+member.getPhone()+"','"+member.getAddress()+"','"+member.getHobby()+"',DEFAULT)";
			// DML의 경우 성공한 행의 갯수가 리턴, 메소드는 executeUpdate() 사용
			int result = stmt.executeUpdate(query);
			// 다 쓴 자원해제
			if(result>0) {
				//commit; - 자동커밋중임
				System.out.println("입력성공!");
			}else {
				//rollback;
				System.out.println("입력실패ㅠㅜ");
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				//예외가 발생하든 안하든 무조건 실행
				// 자원반납을 통해서 오류 발생 방지
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//2. 회원정보 전체출력
	public List<Member> selectList() {
		/*
		 * 1. 드라이버 등록
		 * 2. 연결생성
		 * 3. Statement 생성
		 * 4. SQL문 전송
		 * 5. 결과받기
		 */
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset =null;
		// DB에서 가져온 값 넘겨줘야 하니까
		List<Member> mList = null;
		try {
			Class.forName(DRIVER_NAME);//드라이버등록
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 연결생성
			stmt = conn.createStatement();
			String query = "SELECT * FROM MEMBER_TBL";//쿼리문작성
			//실행
			rset = stmt.executeQuery(query); // SELECT는 executeQuery(query)
			//후처리
			//mList 값이 null이면 안되니까 new ArrayList<>();
			mList = new ArrayList<Member>();
			while(rset.next()) {
				//rset은 바로 못쓰니까 Member
				Member member = this.rsetToMember(rset);
				//비어있으면안되므로 set
				// member에 다 담고 List에 담아야하니까
				mList.add(member);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//자원해제 close();
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mList;
	}
	// 3. 아이디로 회원검색
	public Member selectOne(String memberId) {
		Connection conn = null;
		Statement stmt = null;
		//select 니까 ResultSet
		ResultSet rset = null;
		// try 안에서 쓴 변수는 return 안되니까 try밖에서 Member member = null;
		Member member =null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.createStatement();
			String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = " + "'"+ memberId +"'";
			rset = stmt.executeQuery(query);
			if (rset.next()) member = rsetToMember(rset);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// try 안에서 쓴 변수는 return 안되니까 try밖에서 Member member = null;
		// 호출한곳에서 써야하니까 return member, MemberController : 32
		return member;
	}
	// 5. 회원정보 삭제
		public int deleteMember(String memberId) {
			Connection conn = null;
			Statement stmt = null;
			//try 안에서 쓴 변수는 return 안되니까 try 밖에서 int result = 0;
			int result = 0;
			try {
				// 1. 드라이버 등록
				Class.forName(DRIVER_NAME);
				// 2. 연결생성
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				// 3. statement 생성
				stmt = conn.createStatement();
				conn.setAutoCommit(false); // 오토커밋 꺼주는거
				// 문자열은 '홑따옴표로 감싸야 하니까 "'"
				String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
				// 4. 쿼리문 전송 및 5. 결과받기
				// DML의 결과값은 성공한 행의 갯수니까 int result
				// 쿼리 실행 메소드는 DML이니까 executeUpdate(query);
				result = stmt.executeUpdate(query);
				//쿼리 성공하면 커밋, 실패하면 롤백해야 하므로 if(result >0)
				if(result > 0) {
					//커밋
					conn.commit();
				}else {
					//롤백
					conn.rollback();
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					conn.close();
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//try 안에서 쓴 변수는 return 안되니까 try 밖에서 int result = 0;
			// 호출한 곳에서 써야하니까 return member, MemberController : 25;
			return result;
		}

	
	
	// return이 있으니까 void 대신에 Member
	public Member rsetToMember(ResultSet rset) throws SQLException {
		Member member =new Member();
		//비어있으면안되므로 set
		// Resultset에서 값을 가져와야 하니까 rset.getString("컬럼명")
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
		// member에 다 담았고 호출한 곳에서 써야하니까 return member;
		return member;
	}

	public int updateMember(Member modifyInfo) {
		// finally 에서 close(); 하니까
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		
		try {
			//드라이버 생성
			Class.forName(DRIVER_NAME);
			// 연결생성
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			conn.setAutoCommit(false);
			// statement 생성
			stmt = conn.createStatement();
			// 문자열 홑따옴표 감싸기
			String query = "UPDATE MEMBER_TBL SET MEMBER_PW = '"+modifyInfo.getMemberPw()
						+"', EMAIL ='"+modifyInfo.getEmail()
						+"', PHONE = '"+modifyInfo.getPhone()
						+"', ADDRESS = '"+modifyInfo.getAddress()
						+"', HOBBY = '"+modifyInfo.getHobby()
						+"' WHERE MEMBER_ID = '"+modifyInfo.getMemberId()+"'";
			result = stmt.executeUpdate(query);
			if(result >0 ) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	

}
