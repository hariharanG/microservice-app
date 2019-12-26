package com.hari.xxx.web.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hari.xxx.util.RSAKeyGenerationUtil;
import com.vasco.image.exception.ImageGeneratorSDKException;
import com.vasco.image.generator.ImageGeneratorSDK;
import com.vasco.image.generator.ImageGeneratorSDKConstants;

import liquibase.util.StringUtils;

@Controller
public class GenericController {
	
	@Autowired
	RSAKeyGenerationUtil rsaKeyGenUtil;
	
	public GenericController(RSAKeyGenerationUtil rsaKeyGenUtil) {
		this.rsaKeyGenUtil = rsaKeyGenUtil;
	}
	
	@PostMapping(value = "/login/key")
	public @ResponseBody String getCryptoParameters() {
		return rsaKeyGenUtil.getPublicKey();
	}

	public String encodeToBase64String(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();
			imageString = Base64.encodeBase64String(imageBytes);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}

	public BufferedImage generateCrontoImage(String inputValue) throws ImageGeneratorSDKException {
		return ImageGeneratorSDK.generateDynamicCrontoImage(ImageGeneratorSDKConstants.CRONTOSIGN_MAX_SQUARE_SIZE,
				inputValue,true);
	}
	
	public String testVascoCronTo(String deviceRegisterCronTo) throws ImageGeneratorSDKException {
		//String vascoAddDeviceCronTo="00415FE351E0BCC708A58C9A8BE756EC078CA2B66513C7D9F77B3FBCC93447FE74FBEBAEB45F6D9FAAF1D183AEFCB1AD2DDC3E3E28D15B1BFB71E1164517";
		if(StringUtils.isNotEmpty(deviceRegisterCronTo)) {
			deviceRegisterCronTo = "00005FE351E0BC6A5C13DFA81BD1B1017D916CA08303E09D7EB5AA11BFA89C083F29A7B6EDC5A063A5C1E057616C7A2F9B1E520D50453808017B010346445202100E7B8D632E192272E46718D5AAF695180301010401040501060601010701010801050901010A01000B01010C01010E01010F01011001013801033C0101460108470101112F120100130101140101150C524F2020202020202020202016010117040080D0031801002901062A01002B01002C01021147120100130101140102150C534720202020202020202020160101170401DDD4D31801041901011A01011B01011C01002101082201082301082401082901062A01002B01002C01021135120100130101140103150C43522020202020202020202016010117040183D4D31801011901062101062901062A01002B01002C01021147120100130101140104150C5343202020202020202020201601011704018314D01801041901101A01101B01101C01102101102201102301102401102901062A01002B01002C01023D35120100130101140101150C41435449564154494F4E20201601011704018314D01801011901102101102901082A01002B01002C0102";
		}
		String base64Cronto = encodeToBase64String(generateCrontoImage(deviceRegisterCronTo),"jpeg");
		//inputMap.put("CRONTO_IMAGE",base64Cronto);
		return base64Cronto;
	}
}
