package org.educama.services.flightinformation.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MapperController {

	@RequestMapping("/csvUpload")
	public String uploadCsv(Model model) {
		model.addAttribute("message", "message");
		return "csvUpload";
	}
}
