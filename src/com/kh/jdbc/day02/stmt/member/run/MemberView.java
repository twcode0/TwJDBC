package com.kh.jdbc.day02.stmt.member.run;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day02.stmt.member.controller.MemberController;
import com.kh.jdbc.day02.stmt.member.model.vo.Member;

public class MemberView {
	// View 클래스에서 계속 쓸거니
	// 필드에 mController 두고
	MemberController mController;
	
	public MemberView() {
		//생성자에서 초기화해줌
		mController = new MemberController();
	}

	
	public void startProgram() {
		end:
		while(true) {
			int value =this.printMainMenu();
			switch(value) {
				case 1 : 
					// 1을 눌렀다면 회원의 정보를 입력받아야함
					Member member = this.inputMember(); // inputMember()를 실행시켜 정보받기
					// ID부터 HOBBY까지 저장된 member 객체를 컨트롤러로 전달
					mController.insertMember(member);// 매개변수가 없는 상태임() 소괄호 안에 있는 값이 비어있음 Member member로 매개변수 값을 지정해준다.
					break;
				case 2 : 
					// 2를 눌렀다면 회원의 전체 정보를 출력해야함
					// 1. DB에서 데이터 가져오기, 전체 회원 정보니까 여러개, 여러개니까 리스트 List, 멤버니까 List<Member>
					List<Member> mList = mController.printAllMember();
					// 2. view의 메소드를 이용해서 출력하기
					this.printAllMembers(mList);
					break end;
				case 3 :
					// 3을 눌렀다면 회원의 정보를 검색해야함(아이디로 검색)
					// 사용자가 검색한 아이디 입력받아야 되니까 inputMember();
					String memberId =this.inputMemberId();
					// 입력받은 아이디로 디비에서 검색해와야 하니까 pringOneMember();
					// 컨트롤러로 전달해야하니까 printOneMember(memberId);
					member = mController.printOneMember(memberId);
					// 디비에서 가져온 값을 출력해야 하니까 printOneMember
					this.printOneMember(member);
					break;
				case 4 :
					// 4를 눌렀다면 회원의 정보를 수정해야함(아이디로 정보가 존재하는지 확인 후 있으면 수정 없으면 안함)
					// 수정할 아이디 입력받아야하니까 inputMember();
					memberId = inputMemberId(); // this 생략가능
					// 존재하는 정보만 수정로직을 타야되니까, printOneMember() 호출
					// memberId 전달해야되니까 printOneMember(memberId);
					// DB에서 가져온 값 저장해야 되니까 member = mController.printOneMember(memberId);
					member = mController.printOneMember(memberId);
					// DB에서 데이터를 가져왔는지 체크해야되니까 if(member != null)
					// 데이터가 없다면 member는 null일 것임.
					if (member != null) {
						// 수정할 때에는 수정할 정보를 입력해야 되니까 moidfyMember(member);
						// 즉 수정할 정보를 가지고 있는 member 객체가 필요함.
						Member modifyInfo = this.inputModifyMember();
						// UPDATE할때에는 가장 중요한 것  WHERE 조건절 이니까, WHERE에 들어갈 데이터를 전달해줘야함.
						// modifyMember(member)에서 modifyInfo에 memberId를 꼭 넣어주어야 하니까, modifyInfo.setMemberId(memberId);
						modifyInfo.setMemberId(memberId);
						// DML의 결과는 int니까 int result
						int result = mController.modifyMember(modifyInfo);
						if (result>0) {
							this.displayMessage("수정 성공!");
						}else this.displayMessage("수정 실패!");
					} else {
						this.displayMessage("존재하지 않는 정보입니다.");
					}
					break;
				case 5 :
					// 5를 눌렀다면 회원의 정보를 삭제해야함(아이디로 검색)
					// 사용자가 검색한 아이디를 입력받아야 하니까 inputMember();
					memberId = this.inputMemberId();
					// 존재하는 정보만 삭제로직을 타야되니까, printOneMember() 호출
					// memberId 전달해야되니까 printOneMember(memberId);
					// DB에서 가져온 값 저장해야 되니까 member = mController.printOneMember(memberId);
					member = mController.printOneMember(memberId);
					// DB에서 데이터를 가져왔는지 체크해야되니까 if(member != null)
					// 데이터가 없다면 member는 null일 것임.
					if(member != null) {
					//입력받은 아이디로 디비에서 삭제해야 하니까 removeMember();
					// 컨트롤러로 전달해야 하니까 removeMember(memberId);
					// DML의 결과는 int니까 int result
						int result = mController.removeMember(memberId);
						if (result>0) {
							this.displayMessage("삭제 성공!");
						}else this.displayMessage("삭제 실패!");
					} else {	
						this.displayMessage("존재하지 않는 정보 입니다.");
					}
					break;
			}
		}
	}
	private Member inputModifyMember() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 회원 정보 수정 ======");
		System.out.print("비밀번호 : ");
		String memberPw =sc.next();
		System.out.print("이메일 : ");
		String email =sc.next();
		System.out.print("전화번호 : ");
		String phone =sc.next();
		System.out.print("주소 : ");
		sc.nextLine();
		String address =sc.nextLine();
		System.out.print("취미 : ");
		String hobby =sc.nextLine();
		Member member = new Member();
		member.setMemberPw(memberPw);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);
		member.setHobby(hobby);
		return member;
	}


	// MemberView : 57, 59
	private void displayMessage(String msg) {
		System.out.println("[서비스 결과] : " + msg);
	}


	//MemberView : 46
	private void printOneMember(Member member) {
		System.out.println("=== === 회원 전체 출력 === ===(아이디로 검색)");
		System.out.printf("이름 : %s, 나이 :%d"
				+ ", 아이디 : %s, 성별 : %s, 이메일 : %s"
				+ ", 전화번호 : %s, 주소 : %s, 취미 : %s"
				+ ", 가입날짜 : %s\n"
				,member.getMemberName()
				,member.getAge()
				,member.getMemberId()
				,member.getGender()
				,member.getEmail()
				,member.getPhone()
				,member.getAddress()
				,member.getHobby()
				,member.getRegDate());
		
	}


	private String inputMemberId() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 입력 : ");
		String memberId = sc.next();
		return memberId;
	}


	// MemberView 36열 
	private void printAllMembers(List<Member> mList) {
		System.out.println("=== === 회원 정보 전체 출력 === ===");
		for(Member member : mList) {
			System.out.printf("이름 : %s, 나이 :%d"
					+ ", 아이디 : %s, 성별 : %s, 이메일 : %s"
					+ ", 전화번호 : %s, 주소 : %s, 취미 : %s"
					+ ", 가입날짜 : %s\n"
					,member.getMemberName()
					,member.getAge()
					,member.getMemberId()
					,member.getGender()
					,member.getEmail()
					,member.getPhone()
					,member.getAddress()
					,member.getHobby()
					,member.getRegDate());
		}
	}

	//MemberView : 27
	private Member inputMember() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 회원 정보 등록 ======");
		System.out.print("아이디 : ");
		String memberId =sc.next();
		System.out.print("비밀번호 : ");
		String memberPw =sc.next();
		System.out.print("이름 : ");
		String memberName =sc.next();
		System.out.print("성별 : ");
		String gender =sc.next();
		System.out.print("나이 : ");
		int age = sc.nextInt();
		System.out.print("이메일 : ");
		String email =sc.next();
		System.out.print("전화번호 : ");
		String phone =sc.next();
		System.out.print("주소 : ");
		sc.nextLine();
		String address =sc.nextLine();
		System.out.print("취미 : ");
		String hobby =sc.nextLine();
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setGender(gender);
		member.setAge(age);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);
		member.setHobby(hobby);
		return member;
	}


	public int printMainMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 회원 관리 프로그램 ======");
		System.out.println("1. 회원가입");
		System.out.println("2. 전체 회원 조회");
		System.out.println("3. 회원정보 검색");
		System.out.println("4. 회원 정보수정");
		System.out.println("5. 회원 탈퇴");
		System.out.println("0. 종료");
		System.out.print("메뉴 선택 : ");
		int choice = sc.nextInt();
		return choice;
	}
}
