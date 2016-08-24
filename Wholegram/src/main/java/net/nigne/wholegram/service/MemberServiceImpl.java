package net.nigne.wholegram.service;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.persistance.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	@Inject
	private MemberDAO dao;

	/*로그인 시, id/pw 비교*/
	@Override
	public int compare(MemberVO vo_chk) {
		return dao.compare( vo_chk );
	}

	/*user페이지 , 프로필 편집시 유저정보 가져옴*/
	@Override
	public MemberVO MemInfo(String user_id) {
		return dao.MemInfo(user_id);
	}

	/*유저 정보 업데이트시 Id중복 확인*/
	@Override
	public int compareId(String id) {
		return dao.compareId(id);
	}
	
	/*유저 정보 업데이트시 Email중복 확인*/
	@Override
	public int compareEmail(String email) {
		return dao.compareEmail(email);
	}
	
	/*유저 정보 업데이트*/
	@Override
	public void updateUser(MemberVO vo) {
		dao.updateUser(vo);
	}

	/*비밀번호 변경시 - 원래 비밀번호 가져오기*/
	@Override
	public String checkPasswd(int mem_no) {
		return dao.checkPasswd(mem_no);
	}

	/*비밀번호 변경시 - 원래 비밀번호와 이전 비밀번호 일치확인*/
	@Override
	public int comparePasswd(String passwd_real, String passwd) {
		int result;
		if(passwd_real.equals(passwd)) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}

	/*비밀번호 변경*/
	@Override
	public void updatePasswd(MemberVO vo) {
		dao.updatePasswd(vo);
	}
	
	/*Following 하고있는 유저 정보(id, profile사진)을 가져온다*/
	@Override
	public List<MemberVO> getFollowinguser_Profile(List<String> user_ids) {
		return dao.getFollowinguser_Profile(user_ids);
	}
	
	@Override
	public List<MemberVO> getNewPerson(String user_id) {
		return dao.getNewPerson(user_id);
	}

	@Override
	public List<MemberVO> getKnowablePerson(String user_id) {
		return dao.getKnowablePerson(user_id);
	}

	/*사용자 프로필 이미지 크기 resize*/
	@Override
	public byte[] reSizeProfileImg(MultipartFile mpf) {

		byte[] resizeFile = null;
		
		/* 1. How to convert MultipartFile to File */
		File convFile = convert(mpf);
		
		/* 2. How to convert MultipartFile to File */
		//File convFile = multipartToFile(mpf);
		
		try{
			BufferedImage originalImage = ImageIO.read(convFile);
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			
	    	int IMG_WIDTH = originalImage.getWidth();
	    	int IMG_HEIGHT = originalImage.getHeight();
			
			if((IMG_WIDTH > 1000) && (IMG_HEIGHT > 1000) && (convFile.length() > 500000)) {	// 용량이 500KB이상일 경우 resize 해준다.

				BufferedImage resizeImageJpg = resizeImage(originalImage, type, IMG_WIDTH, IMG_HEIGHT);
				ImageIO.write(resizeImageJpg, "jpg", convFile); 
					
				BufferedImage resizeImagePng = resizeImage(originalImage, type, IMG_WIDTH, IMG_HEIGHT);
				ImageIO.write(resizeImagePng, "png", convFile); 
					
				BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type, IMG_WIDTH, IMG_HEIGHT);
				ImageIO.write(resizeImageHintJpg, "jpg", convFile); 
					
				BufferedImage resizeImageHintPng = resizeImageWithHint(originalImage, type, IMG_WIDTH, IMG_HEIGHT);
				ImageIO.write(resizeImageHintPng, "png", convFile); 
				
				System.out.println("수정 가로 : " + resizeImageJpg.getWidth());
				System.out.println("수정 높이 : " + resizeImageJpg.getHeight());
				
				resizeFile = convertToArray(resizeImageJpg, mpf.getContentType());
			} else {
				resizeFile = mpf.getBytes();	// 용량이 300KB 이하일 경우 원본을 그대로 저장
			}
		}catch(IOException e){
			System.out.println("exception : " + e.getMessage());
		}
		return resizeFile;
	}
	
	/* 파일 용량(크기) 조절 */
    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT){
    	
		if(IMG_WIDTH > IMG_HEIGHT) {						// 가로가 더 클 때
			double result = (double)IMG_WIDTH / (double)IMG_HEIGHT;
			System.out.println("result : " + result);
			if(result >= 2) {								// 가로 세로 비율 2 : 1
				IMG_WIDTH = 1500;
				IMG_HEIGHT = 750;
			} else if(result >= 1.8 && result < 2) {		// 1.8 : 1
				IMG_WIDTH = 1500;
				IMG_HEIGHT = 789;
			} else if(result >= 1.6 && result < 1.8){		// 1.6 : 1
				IMG_WIDTH = 1500;
				IMG_HEIGHT = 882;
			} else if(result >= 1.5 && result < 1.6) {		// 1.5 : 1
				IMG_WIDTH = 1500;
				IMG_HEIGHT = 967;
			} else if(result >= 1.4 && result < 1.5) {		// 1.4 : 1
				IMG_WIDTH = 1500;
				IMG_HEIGHT = 1034;
			} else if(result >= 1.3 && result < 1.4) {		// 1.3 : 1
				IMG_WIDTH = 1500;
				IMG_HEIGHT = 1111;
			} else if(result >= 1.25 && result < 1.3) {		// 1.25 : 1
				IMG_WIDTH = 1500;
				IMG_HEIGHT = 1171;
			} else if(result > 1.15 && result < 1.25) {		// 1.17 : 1
				IMG_WIDTH = 1500;
				IMG_HEIGHT = 1250;
			} else if(result > 1 && result < 1.15) {		// 1.1 : 1
				IMG_WIDTH = 1500;
				IMG_HEIGHT = 1339;
			} 
		} else if(IMG_WIDTH < IMG_HEIGHT) {					// 세로가 더 클 때
			double result = (double)IMG_HEIGHT / (double)IMG_WIDTH;
			System.out.println("result : " + result);
			if(result >= 2) {								// 세로 가로 비율 2 : 1
				IMG_HEIGHT = 1500;
				IMG_WIDTH = 750;
			} else if(result >= 1.8 && result < 2) {		// 1.8 : 1
				IMG_HEIGHT = 1500;
				IMG_WIDTH = 789;
			} else if(result >= 1.6 && result < 1.8){		// 1.6 : 1
				IMG_HEIGHT = 1500;
				IMG_WIDTH = 882;
			} else if(result >= 1.5 && result < 1.6) {		// 1.5 : 1
				IMG_HEIGHT = 1500;
				IMG_WIDTH = 967;
			} else if(result >= 1.4 && result < 1.5) {		// 1.4 : 1
				IMG_HEIGHT = 1500;
				IMG_WIDTH = 1034;
			} else if(result >= 1.3 && result < 1.4) {		// 1.3 : 1
				IMG_HEIGHT = 1500;
				IMG_WIDTH = 1111;
			} else if(result >= 1.25 && result < 1.3) {		// 1.25 : 1
				IMG_HEIGHT = 1500;
				IMG_WIDTH = 1171;
			} else if(result > 1.15 && result < 1.25) {		// 1.17 : 1
				IMG_HEIGHT = 1500;
				IMG_WIDTH = 1250;
			} else if(result > 1 && result < 1.15) {		// 1.1 : 1
				IMG_HEIGHT = 1500;
				IMG_WIDTH = 1339;
			}
		} else {											// 1:1
			IMG_WIDTH = 1500;
			IMG_HEIGHT = 1500;
		}
    	
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		return resizedImage;
    }
	
    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT){
		
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		
		return resizedImage;
    }	
	
	/*BufferedImage를 byte[]로 변환*/
    private static byte[] convertToArray(BufferedImage image, String contentType) throws IOException { 
        byte[] imageInByte; 
 
        String typeName = "jpg"; 
        if (contentType.equals(MediaType.IMAGE_PNG)) 
            typeName = "png"; 
 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        ImageIO.write(image, typeName, baos); 
        baos.flush(); 
        imageInByte = baos.toByteArray(); 
        baos.close(); 
 
        return imageInByte; 
    } 
	
	/*MultipartFile을 File로 변환*/
	public File convert(MultipartFile file)
	{    
	    File convFile = new File(file.getOriginalFilename());
	    try {
			convFile.createNewFile();
		    FileOutputStream fos = new FileOutputStream(convFile); 
		    fos.write(file.getBytes());
		    fos.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    return convFile;
	}
	
	/*MultipartFile을 File로 변환*/
	public File multipartToFile(MultipartFile multipart)
	{
        File convFile = new File( multipart.getOriginalFilename());
        try {
			multipart.transferTo(convFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
        return convFile;
	}

	/*프로필 이미지를 기본으로 변경*/
	@Override
	public void setDefaultProfileImage(String user_id) {
		dao.setDefaultProfileImage(user_id);
	}

	@Override
	public List<MemberVO> getRandomUser(String user_id) {
		return dao.getRandomUser(user_id);
	}
}
