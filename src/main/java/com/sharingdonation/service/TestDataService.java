package com.sharingdonation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sharingdonation.constant.Role;
import com.sharingdonation.entity.Area;
import com.sharingdonation.entity.Category;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationBoard;
import com.sharingdonation.entity.DonationBoardComment;
import com.sharingdonation.entity.DonationBoardHeart;
import com.sharingdonation.entity.DonationBoardImg;
import com.sharingdonation.entity.DonationHeart;
import com.sharingdonation.entity.DonationImg;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.SharingBoard;
import com.sharingdonation.entity.SharingBoardComment;
import com.sharingdonation.entity.SharingBoardHeart;
import com.sharingdonation.entity.SharingBoardImg;
import com.sharingdonation.entity.SharingHeart;
import com.sharingdonation.entity.SharingImg;
import com.sharingdonation.entity.Story;
import com.sharingdonation.repository.AreaRepository;
import com.sharingdonation.repository.CategoryRepository;
import com.sharingdonation.repository.DonationBoardCommentRepository;
import com.sharingdonation.repository.DonationBoardHeartRepository;
import com.sharingdonation.repository.DonationBoardImgRepository;
import com.sharingdonation.repository.DonationBoardRepository;
import com.sharingdonation.repository.DonationHeartRepository;
import com.sharingdonation.repository.DonationImgRepository;
import com.sharingdonation.repository.DonationRepository;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.SharingBoardCommentRepository;
import com.sharingdonation.repository.SharingBoardHeartRepository;
import com.sharingdonation.repository.SharingBoardImgRepository;
import com.sharingdonation.repository.SharingBoardRepository;
import com.sharingdonation.repository.SharingHeartRepository;
import com.sharingdonation.repository.SharingImgRepository;
import com.sharingdonation.repository.SharingRepository;
import com.sharingdonation.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TestDataService {
	
	private final PasswordEncoder pwdEncoder;

	private final CategoryRepository categoryRepo;
	private final AreaRepository areaRepository;
	private final MemberRepository memberRepo;
	private final SharingRepository sharingRepo;
	private final SharingImgRepository sharingImgRepo;
	private final SharingHeartRepository sharingHeartRepo;
	private final SharingBoardRepository sharingBoardRepo;
	private final SharingBoardHeartRepository sharingBoardHeartRepo;
	private final SharingBoardImgRepository sharingBoardImgRepo;
	private final SharingBoardCommentRepository sharingBoardCommentRepo;
	private final DonationRepository donationRepo;
	private final DonationImgRepository donationImgRepo;
	private final DonationHeartRepository donationHeartRepo;
	private final DonationBoardRepository donationBoardRepo;
	private final DonationBoardHeartRepository donationBoardHeartRepo;
	private final DonationBoardImgRepository donationBoardImgRepo;
	private final DonationBoardCommentRepository donationBoardCommentRepo;
	private final StoryRepository storyRepo;
	
	private static Random random = new Random();
	
	private static final String[] CATEGORIES = {"가전", "생활", "유아", "의류", "스포츠", "도서", "취미", "기타"}; 
	private static final String[] AREAS = {"강서구", "마포구", "양천구", "구로구", "은평구", "서대문구", "영등포구", "금천구", "동작구", "관악구", "종로구", "중구", "용산구", "서초구", "강북구", "성북구", "도봉구", "동대문구", "성동구", "강남구", "노원구", "중랑구", "광진구", "송파구", "강동구"};

	public void initData() {
		Arrays.asList(CATEGORIES).forEach(c -> {
			Category cat = categoryRepo.findByCategoryName(c);
			if (cat == null) addCategory(c);
		});

		Arrays.asList(AREAS).forEach(a -> {
			Area area = areaRepository.findByGugun(a);
			if (area == null) addArea(a);
		});
	}
	
	public void generateTestData() {
		// 카테고리
		Arrays.asList(CATEGORIES).forEach(c -> addCategory(c));
		List<Category> categoryList = categoryRepo.findAll();
		
		// 지역 
		Arrays.asList(AREAS).forEach(a -> addArea(a));
		List<Area> areaList = areaRepository.findAll();
		
		//회원
		IntStream.range(0, 10).forEach(i -> addMember(Role.USER, i));
		IntStream.range(0, 10).forEach(i -> addMember(Role.COM, i));
		addMember(Role.ADMIN, 0);
		List<Member> memberList = memberRepo.findAll();
		List<Member> userList = memberList.stream().filter(member -> member.getRole() == Role.USER).toList();
		List<Member> comList = memberList.stream().filter(member -> member.getRole() == Role.COM).toList();

		// 나눔
		IntStream.range(0, 100).forEach(i -> {
			Category c = categoryList.get(random.nextInt(categoryList.size()));
			Member m = userList.get(random.nextInt(userList.size()));
			Area a = areaList.get(random.nextInt(areaList.size()));
			Sharing sharing = addSharing(c, m, a, i < 80);
			// 이미지
			for (int j = 0; j < 2; j++) {
				addSharingImg((j + 1) + (i * 2), sharing, j == 0);
			}
			// 사연
			if (sharing.getConfirmYn().equals("Y")) {
				int storyCount = 0;
				while (storyCount < 3) {
					Member member = userList.get(random.nextInt(userList.size()));
					Story findStory = storyRepo.findBySharingIdAndMemberId(sharing.getId(), member.getId());
					if (findStory != null || sharing.getMember().getId().equals(member.getId())) continue;
					
					Story adoptedStory = storyRepo.findBySharingIdAndChooseYn(sharing.getId(), "Y");
					if (adoptedStory != null) break;
					Story story = addStory(sharing, member, random.nextInt(10) > 8);
					storyCount++;
					if (story.getChooseYn().equals("Y")) {
						sharing.setDone("Y");
						sharing.setUpDateTime(LocalDateTime.now());
					}
				}
			}
		});
		List<Sharing> sharingList = sharingRepo.findAll();
		
		// 나눔 완료
		sharingList.stream()
			.filter(sharing -> sharing.getDone().equals("Y"))
			.forEach(sharing -> {
				if (random.nextInt(100) > 50)
					addSharedBoard(sharing);
			});;
		List<SharingBoard> sharingBoardList = sharingBoardRepo.findAll();
		
		// 나눔 완료 이미지
		for (int i = 0; i < sharingBoardList.size(); i ++) {
			for (int j = 0; j < 2; j++) {
				addSharingBoardImg((j + 1) + (i * 2), sharingBoardList.get(i), j == 0);
			}
		}
		
		// 회원 반복
		for (int i = 0; i < memberList.size(); i++) {
			Member member = memberList.get(i);
			// 나눔 좋아요
			int sharingHeartCount = 0;
			while (sharingHeartCount < 20) {
				Sharing sharing = sharingList.get(random.nextInt(sharingList.size()));
				boolean isLiked = sharingHeartRepo.findAll().stream().filter(heart -> heart.getSharing().getId() == sharing.getId() && heart.getMember().getId() == member.getId()).count() > 0;
				if (sharing.getMember().getId() != member.getId() || !isLiked) {
					addSharingHeart(member, sharing);
					sharingHeartCount++;
				}
			}
			// 나눔 완료 좋아요
			
			int sharingBoardHeartCount = 0;
			while (sharingBoardHeartCount < 5) {
				SharingBoard sharingBoard = sharingBoardList.get(random.nextInt(sharingBoardList.size()));
				boolean isLiked = sharingBoardHeartRepo.findAll().stream().filter(heart -> heart.getSharingBoard().getId() == sharingBoard.getId() && heart.getMember().getId() == member.getId()).count() > 0;
				if (!isLiked) {
					addSharingBoardHeart(member, sharingBoard);
					sharingBoardHeartCount++;
				}
			}
			// 나눔 완료 댓글
			int sharingBoardCommentCount = 0;
			while (sharingBoardCommentCount < 2) {
				SharingBoard sharingBoard = sharingBoardList.get(random.nextInt(sharingBoardList.size()));
				addSharingBoardComment(sharingBoard, member);
				sharingBoardCommentCount++;
			}
		}
		
		// 기부
		IntStream.range(0, 100).forEach(i -> {
			Member m = memberList.get(random.nextInt(memberList.size()));
			Donation donation = addDonation(m, i < 80);
			// 이미지
			for (int j = 0; j < 2; j++) {
				addDonationImg((j + 1) + (i * 2), donation, j == 0);
			}
		});
		List<Donation> donationList = donationRepo.findAll();
		
		// 기부 이미지
//		for (int i = 0; i < donationList.size(); i ++) {
//			addDonationImg(i + 1, donationList.get(i), i == 0);
//		}
//		for (int i = 0; i < donationList.size(); i ++) {
//			for (int j = 0; j < 2; j++) {
//				addDonationImg((j + 1) + (i * 2), donationList.get(i), j == 0);
//			}
//		}
		
		// 기부 좋아요
		for (Member member: memberList) {
			int count = 0;
			while (count < 20) {
				Donation donation = donationList.get(random.nextInt(donationList.size()));
				boolean isLiked = donationHeartRepo.findAll().stream().filter(heart -> heart.getDonation().getId() == donation.getId() && heart.getMember().getId() == member.getId()).count() > 0;
				if (donation.getMember().getId() != member.getId() || !isLiked) {
					addDonationHeart(member, donation);
					count++;
				}
			}
		}
		
		
		// 기부 완료
		
		// 기부 완료 좋아요
		
		// 기부 완료 이미지
		
		// 기부 완료 댓글
		
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
	public void addMember(Role role, int i) {
		Member member = new Member();
		String username = role.toString().toLowerCase() + i;

		member.setEmail(username + "@email.com");
		member.setPassword(pwdEncoder.encode("test1234"));
		member.setName(username);
		member.setCellphone(randomPhone());
		member.setZipCode((random.nextInt(90000) + 10000) + "");
		member.setAddress(randomString(5) + " " + randomString(5));
		member.setAddressDetail(randomString(13));
		member.setNickName(username + "_nick");
		member.setRegTime(LocalDateTime.now());
		member.setDelYn("N");

		member.setRole(role);

		if (role == Role.COM) {
			member.setComNum(randomComNum());
			member.setFax(randomFax());
		} else if (role == Role.USER || role == Role.ADMIN) {
			member.setComNum("");
			member.setFax("");
			member.setBirth(LocalDate.now());
		}
		memberRepo.save(member);
	}
//		나눔
	public Sharing addSharing(Category category, Member member, Area area, boolean isConfirmed) {
		Sharing sharing = new Sharing();
		sharing.setCategory(category);
		sharing.setMember(member);
		sharing.setArea(area);
		sharing.setName(randomString(8));
		sharing.setDetail(randomString(50));
		sharing.setRegTime(LocalDateTime.now());
		sharing.setCreateBy("TEST");
		sharing.setDone("N");
		sharing.setDelYn("N");
		
		if (isConfirmed) {
			sharing.setPoint(random.nextInt(1500) + 500);
			sharing.setConfirmYn("Y");
			sharing.setStartDate(LocalDate.now());
			sharing.setEndDate(LocalDate.now().plusDays(7));
			sharing.setUpDateTime(LocalDateTime.now());
			sharing.setModifyBy("TEST");
		} else {
			sharing.setPoint(0);
			sharing.setConfirmYn("N");
		}
		
		sharingRepo.save(sharing);
		return sharing;
	}
//		나눔 이미지
	public void addSharingImg(int i, Sharing sharing, boolean isRepImg) {
		SharingImg sharingImg = new SharingImg();
		sharingImg.setSharing(sharing);
		sharingImg.setImgName("img-" + i + ".jpg");
		sharingImg.setOriImgName("img-" + i + ".jpg");
		sharingImg.setImgUrl("/images/sharing/dummy/img-" + i + ".jpg");
		sharingImg.setRepimgYn(isRepImg ? "Y" : "N");

		sharingImgRepo.save(sharingImg);
	}

//		나눔 좋아요
	public void addSharingHeart(Member member, Sharing sharing) {
		SharingHeart sharingHeart = new SharingHeart();
		sharingHeart.setMember(member);
		sharingHeart.setSharing(sharing);
		sharingHeart.setRegTime(LocalDateTime.now());
		
		sharingHeartRepo.save(sharingHeart);
	}
//		나눔 완료
	public SharingBoard addSharedBoard(Sharing sharing) {
		SharingBoard sharingBoard = new SharingBoard();
		
		sharingBoard.setSharing(sharing);
		sharingBoard.setSubject(randomString(8));
		sharingBoard.setContent(randomString(50));
		sharingBoard.setRegTime(LocalDateTime.now());
		
		sharingBoardRepo.save(sharingBoard);
		
		return sharingBoard;
	}
//		나눔 완료 좋아요
	public void addSharingBoardHeart(Member member, SharingBoard sharingBoard) {
		SharingBoardHeart heart = new SharingBoardHeart();
		heart.setSharingBoard(sharingBoard);
		heart.setMember(member);
		heart.setRegTime(LocalDateTime.now());
		
		sharingBoardHeartRepo.save(heart);
	}
//		나눔 완료 이미지
	public void addSharingBoardImg(int i, SharingBoard board, boolean isRepImg) {
		SharingBoardImg boardImg = new SharingBoardImg();
		boardImg.setSharingBoard(board);
		boardImg.setImgName("img-" + i + ".jpg");
		boardImg.setOriImgName("img-" + i + ".jpg");
		boardImg.setImgUrl("/images/sharedBoard/dummy/img-" + i + ".jpg");
		boardImg.setRepimgYn(isRepImg ? "Y": "N");

		sharingBoardImgRepo.save(boardImg);
	}
	
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
	public Donation addDonation(Member member, boolean isConfirmed) {
		Donation donation = new Donation();
		donation.setMember(member);
		donation.setDonationName(randomString(10));
		donation.setDonationPerson(randomString(10));
		donation.setDonationTel(randomPhone());
		donation.setSubject(randomString(5));
		donation.setDetail(randomString(50));
		donation.setPrice(random.nextInt(99_000_000) + 1_000_000);
		donation.setZipCode((random.nextInt(90000) + 10000) + "");
		donation.setAddress(randomString(20));
		donation.setAddressDetail(randomString(100));
		donation.setGoalPoint(random.nextInt(500_000) + 1_500_000);
		donation.setDone("N");
		donation.setDelYn("N");
		
		if (isConfirmed) {
			donation.setConfirmYn("Y");
			donation.setStartDate(LocalDate.now());
			donation.setEndDate(LocalDate.now().plusMonths(1));
		} else {
			donation.setConfirmYn("N");
		}
		
		donationRepo.save(donation);
		
		return donation;
	}
//		기부 이미지
	public void addDonationImg(int i, Donation donation, boolean isRepImg) {
		DonationImg donationImg = new DonationImg();
		
		donationImg.setDonation(donation);
		donationImg.setImgName("img-" + i + ".jpg");
		donationImg.setOriImgName("img-" + i + ".jpg");
		donationImg.setImgUrl("/images/donation/dummy/img-" + i + ".jpg");
		donationImg.setRepimgYn(isRepImg ? "Y" : "N");

		donationImgRepo.save(donationImg);
	}

//		기부 좋아요
	public void addDonationHeart(Member member, Donation donation) {
		DonationHeart heart = new DonationHeart();
		heart.setDonation(donation);
		heart.setMember(member);
		heart.setRegTime(LocalDateTime.now());
		
		donationHeartRepo.save(heart);
	}
//		기부 완료
	public void addDonationBoard(Donation donation) {
		DonationBoard board = new DonationBoard();
		board.setDonation(donation);
		board.setSubject(randomString(10));
		board.setContent(randomString(100));
		board.setRegTime(LocalDateTime.now());
		
		donationBoardRepo.save(board);
	}
//		기부 완료 좋아요
	public void addDonationBoardHeart(Member member, DonationBoard board) {
		DonationBoardHeart heart = new DonationBoardHeart();
		heart.setDonationBoard(board);
		heart.setMember(member);
		heart.setRegTime(LocalDateTime.now());
		
		donationBoardHeartRepo.save(heart);
	}
//		기부 완료 이미지
	public void addDonationBoardImg(int i, DonationBoard board, boolean isRepImg) {
		DonationBoardImg boardImg = new DonationBoardImg();
		boardImg.setDonationBoard(board);
		boardImg.setImgName("img-" + i + ".jpg");
		boardImg.setOriImgName("img-" + i + ".jpg");
		boardImg.setImgUrl("/images/donationBoard/dummy/img-" + i + ".jpg");
		boardImg.setRepimgYn(isRepImg ? "Y": "N");

		donationBoardImgRepo.save(boardImg);
	}
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
	public Story addStory(Sharing sharing, Member member, boolean choosen) {
		Story story = new Story();
		story.setSharing(sharing);
		story.setMember(member);
		story.setContent(randomString(100));
		story.setRegTime(LocalDateTime.now());
		story.setChooseYn(choosen ? "Y" : "N");
		story.setDelYn("N");
		
		storyRepo.save(story);
		
		return story;
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
