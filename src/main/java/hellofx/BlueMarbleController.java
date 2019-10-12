package hellofx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.curiousworks.BlueMarble;
import org.json.JSONObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlueMarbleController {

	@FXML
	private ImageView image;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Button enhanced;

	@FXML
	void updateDate(ActionEvent event) {

		BlueMarble blueMarble = new BlueMarble();
//		blueMarble.setDate(datePicker.getValue().getYear() + "-0" + datePicker.getValue().getMonthValue() + "-" + datePicker.getValue().getDayOfMonth());
		blueMarble.setDate(
				"2018" + "-0" + datePicker.getValue().getMonthValue() + "-" + datePicker.getValue().getDayOfMonth());
		blueMarble.setEnhanced(true);
//		Image value = new Image(BlueMarble.getMostRecentImage());
		image.setImage(new Image(blueMarble.getImage()));
	}

	@FXML
	void setNormalPicture(ActionEvent event1) {

		BlueMarble blueMarble = new BlueMarble();
		blueMarble.setDate(datePicker.getValue().getYear() + "-0" + datePicker.getValue().getMonthValue() + "-"
				+ datePicker.getValue().getDayOfMonth());
		blueMarble.setNatural(true);
		image.setImage(new Image(blueMarble.getImage()));

	}

	@FXML
	void validateDate(ActionEvent event2) throws ParseException {
		enhanced.setVisible(true);

		SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = sdfo.parse(LocalDate.now().toString());
		Date d2 = sdfo.parse(datePicker.getValue().getYear() + "-" + datePicker.getValue().getMonthValue() + "-"
				+ datePicker.getValue().getDayOfMonth());
		Date d3 = sdfo.parse("2018-05-31");
		if (d2.compareTo(d1) > 0) {

			System.out.println("You have chosen a date in future. Select the date again.");
		}
		if (d2.compareTo(d3) > 0) {

			enhanced.setVisible(false);
		}
	}

	@FXML
	void blackAndWhite(ActionEvent event2) throws IOException {

		BlueMarble blueMarble = new BlueMarble();
		blueMarble.setDate(datePicker.getValue().getYear() + "-0" + datePicker.getValue().getMonthValue() + "-"
				+ datePicker.getValue().getDayOfMonth());
		blueMarble.setNatural(true);

		Image fxImage = new Image(blueMarble.getImage());
		BufferedImage imageToChange = SwingFXUtils.fromFXImage(fxImage, null);
		// BufferedImage imageToChange = ImageIO.read(new
		// FileInputStream(image.getImage().getUrl().toString()));
		for (int i = 0; i < imageToChange.getWidth(); i++) {
			for (int j = 0; j < imageToChange.getHeight(); j++) {
				// System.out.println(imageToChange.getRGB(i, j));
				// new Color(imageToChange.getRGB(i, j))
				int p = imageToChange.getRGB(i, j);
				int a = (p << 24) & 0xff;
				int r = (p << 16) & 0xff;
				int g = (p << 8) & 0xff;
				int b = p & 0xff;
				int avg = (r + g + b) / 3;

				p = (a << 24) | (avg << 16) | (avg << 8) | avg;
				imageToChange.setRGB(i, j, p);

			}

		}
		Image image1 = SwingFXUtils.toFXImage(imageToChange, null);
		image.setImage(image1);

	}
}
