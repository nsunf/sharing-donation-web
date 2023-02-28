package com.sharingdonation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sharingdonation.constant.Role;
import com.sharingdonation.entity.Area;
import com.sharingdonation.entity.Category;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationBoard;
import com.sharingdonation.entity.DonationBoardComment;
import com.sharingdonation.entity.DonationHeart;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.SharingBoard;
import com.sharingdonation.entity.SharingBoardComment;
import com.sharingdonation.entity.SharingBoardHeart;
import com.sharingdonation.entity.SharingHeart;
import com.sharingdonation.entity.Story;
import com.sharingdonation.repository.AreaRepository;
import com.sharingdonation.repository.CategoryRepository;
import com.sharingdonation.repository.DonationBoardCommentRepository;
import com.sharingdonation.repository.DonationBoardRepository;
import com.sharingdonation.repository.DonationHeartRepository;
import com.sharingdonation.repository.DonationRepository;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.SharingBoardCommentRepository;
import com.sharingdonation.repository.SharingBoardRepository;
import com.sharingdonation.repository.SharingHeartRepository;
import com.sharingdonation.repository.SharingRepository;
import com.sharingdonation.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TestDataService {

	private final CategoryRepository categoryRepo;
	private final AreaRepository areaRepository;
	private final MemberRepository memberRepo;
	private final SharingRepository sharingRepo;
	private final SharingHeartRepository sharingHeartRepo;
	private final SharingBoardRepository sharingBoardRepo;
//	private final SharingBoardHeartRepository sharingBoardHeartRepo;
	private final SharingBoardCommentRepository sharingBoardCommentRepo;
	private final DonationRepository donationRepo;
	private final DonationHeartRepository donationHeartRepo;
	private final DonationBoardRepository donationBoardRepo;
	private final DonationBoardCommentRepository donationBoardCommentRepo;
	private final StoryRepository storyRepo;
	
	private static Random random = new Random();
	
	public void generateTestData() {
		// 카테고라
		String[] category = {"가전", "생활", "유아", "의류", "스포츠", "도서", "취미", "기타"};
		Arrays.asList(category).stream().forEach(c -> addCategory(c));
		List<Category> categoryList = categoryRepo.findAll();
		
		// 지역 
		String[] area = {"강서구", "마포구", "양천구", "구로구", "은평구", "서대문구", "영등포구", "금천구", "동작구", "관악구", "종로구", "중구", "용산구", "서초구", "강북구", "성북구", "도봉구", "동대문구", "성동구", "강남구", "노원구", "중랑구", "광진구", "송파구", "강동구"};
		Arrays.asList(area).stream().forEach(a -> addArea(a));
		List<Area> areaList = areaRepository.findAll();
		
		// 멤버
		IntStream.range(0, 10).forEach(i -> addMember(Role.USER));
		IntStream.range(0, 10).forEach(i -> addMember(Role.COM));
		List<Member> memberList = memberRepo.findAll();

		// 나눔
		IntStream.range(0, 100).forEach(i -> {
			Category c = categoryList.get(random.nextInt(categoryList.size()));
			Member m = memberList.get(random.nextInt(memberList.size()));
			Area a = areaList.get(random.nextInt(areaList.size()));
			addSharing(c, m, a);
		});
		List<Sharing> sharingList = sharingRepo.findAll();
		
		// 기부
		IntStream.range(0, 100).forEach(i -> {
			Member m = memberList.get(random.nextInt(memberList.size()));
			addDonation(m);
		});
		List<Donation> donationList = donationRepo.findAll();
	}

//	카테고리
	public void addCategory(String name) {
		Category category = new Category();
		category.setCategoryName(name);
		category.setImgName("");
		category.setImgUrl("");
		category.setDelYn("N");
		
		categoryRepo.save(category);
		
		System.out.println("카테고리  " + category.getCategoryName() + " 저장됨");
	}
		
//		지역
	public void addArea(String name) {
		Area area = new Area();
		area.setSido("서울시");
		area.setGugun(name);
		
		areaRepository.save(area);

		System.out.println("지역 " + area.getGugun() + " 저장됨");
	}

//		멤버
	public void addMember(Role role) {
		Member member = new Member();
		member.setEmail(randomString(10) + "@email.com");
		member.setPassword("test1234");
		member.setName(randomString(5));
		member.setCellphone(randomPhone());
		member.setZipCode((random.nextInt(90000) + 10000) + "");
		member.setAddress(randomString(5) + " " + randomString(5));
		member.setAddressDetail(randomString(13));
		member.setNickName(randomString(5));
		member.setRegTime(LocalDateTime.now());
		member.setDelYn("N");

		member.setRole(role);

		if (role == Role.COM) {
			member.setComNum(randomComNum());
			member.setFax(randomFax());
		} else if (role == Role.USER) {
			member.setComNum("");
			member.setFax("");
			member.setBirth(LocalDate.now());
		}
		memberRepo.save(member);
	}
//		나눔
	public void addSharing(Category category, Member member, Area area) {
		Sharing sharing = new Sharing();
		sharing.setCategory(category);
		sharing.setMember(member);
		sharing.setArea(area);
		sharing.setName(randomString(8));
		sharing.setDetail(randomString(50));
		sharing.setPoint(0);
		sharing.setRegTime(LocalDateTime.now());
		sharing.setCreateBy("TEST");
		sharing.setConfirmYn("N");
		sharing.setDone("N");
		sharing.setDelYn("N");
		
		sharingRepo.save(sharing);
	}
//		나눔 이미지

//		나눔 좋아요
	public void addSharingHeart(Member member, Sharing sharing) {
		SharingHeart sharingHeart = new SharingHeart();
		sharingHeart.setMember(member);
		sharingHeart.setSharing(sharing);
		sharing.setRegTime(LocalDateTime.now());
		
		sharingHeartRepo.save(sharingHeart);
	}
//		나눔 완료
	public void addSharedBoard(Sharing sharing) {
		SharingBoard sharingBoard = new SharingBoard();
		
		sharingBoard.setSharing(sharing);
		sharingBoard.setSubject(randomString(8));
		sharingBoard.setContent(randomString(50));
		sharingBoard.setRegTime(LocalDateTime.now());
		
		sharingBoardRepo.save(sharingBoard);
	}
//		나눔 완료 좋아요
	public void addSharingBoardHeart(Member member, Sharing sharing) {

	}
//		나눔 완료 이미지
	
//		나눔 완료 댓글
	public void addSharingBoardComment(SharingBoard sharingBoard, Member member) {
		SharingBoardComment comment = new SharingBoardComment();
		comment.setSharingBoard(sharingBoard);
		comment.setMember(member);
		comment.setComment(randomString(50));
		comment.setRegTime(LocalDateTime.now());
		
		sharingBoardCommentRepo.save(comment);
	}
//		기부
	public void addDonation(Member member) {
		Donation donation = new Donation();
		donation.setMember(member);
		donation.setDonationName(randomString(10));
		donation.setDonationPerson(randomString(10));
		donation.setDonationTel(randomPhone());
		donation.setSubject(randomString(5));
		donation.setDetail(randomString(50));
		donation.setPrice(1_000_000);
		donation.setZipCode((random.nextInt(90000) + 10000) + "");
		donation.setAddress(randomString(20));
		donation.setAddressDetail(randomString(100));
		donation.setConfirmYn("N");
		donation.setDone("N");
		donation.setDelYn("N");
		
		donationRepo.save(donation);
	}
//		기부 이미지

//		기부 좋아요
	public void addDonationHeart(Donation donation, Member member) {
		DonationHeart heart = new DonationHeart();
		heart.setDonation(donation);
		heart.setMember(member);
		heart.setRegTime(LocalDateTime.now());
		
		donationHeartRepo.save(heart);
	}
//		기부 완료
	public void addDonatedBoard(Donation donation) {
		DonationBoard board = new DonationBoard();
		board.setDonation(donation);
		board.setSubject(randomString(10));
		board.setContent(randomString(100));
		board.setRegTime(LocalDateTime.now());
		
		donationBoardRepo.save(board);
	}
//		기부 완료 좋아요
//		기부 완료 이미지
// 		기부 완료 댓글
	public void addDonationBoardComment(DonationBoard donationBoard, Member member) {
		DonationBoardComment comment = new DonationBoardComment();
		comment.setDonationBoard(donationBoard);
		comment.setMember(member);
		comment.setComment(randomString(100));
		comment.setRegTime(LocalDateTime.now());
		
		donationBoardCommentRepo.save(comment);
	}
//		사연
	public void addStory(Sharing sharing, Member member) {
		Story story = new Story();
		story.setSharing(sharing);
		story.setMember(member);
		story.setContent(randomString(100));
		story.setRegTime(LocalDateTime.now());
		story.setChooseYn("N");
		
		storyRepo.save(story);
	}

	public String randomString(int length) {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = length;

		String generatedString = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();
		
		return generatedString;
	}
	
	public String randomPhone() {
		int p1 = random.nextInt(900) + 100;
		int p2 = random.nextInt(9000) + 1000;
		return "010-" + p1 + "-" + p2;
	}
	
	public String randomComNum() {
		String n1 = random.nextInt(900) + 100 + "";
		String n2 = random.nextInt(90) + 100 + "";
		String n3 = random.nextInt(9000) + 1000 + "";
		return String.join("-", n1, n2, n3);
	}
	
	public String randomFax() {
		String n1 = random.nextInt(90) + 10 + "";
		String n2 = random.nextInt(900) + 1000 + "";
		String n3 = random.nextInt(9000) + 1000 + "";
		return String.join("-", n1, n2, n3);
	}
	
}
