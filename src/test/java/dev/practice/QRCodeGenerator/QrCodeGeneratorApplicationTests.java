package dev.practice.QRCodeGenerator;

import dev.practice.QRCodeGenerator.utils.QRCodeGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
class QrCodeGeneratorApplicationTests {

	@Value("${qrcode.path}")
	private String qrCodePath;

	@Test
	void contextLoads() {
	}

	@Test
	void testDecodeQrCode() throws Exception{
		Path mockPath = Paths.get(qrCodePath+"1507775039Annonymous-1301341684-QRCODE.png");

		String result = QRCodeGenerator.decodeQrCode(mockPath);

		if(result.isEmpty())
			System.out.println("Failed");
		else
			System.out.println("Success!");
	}

}
