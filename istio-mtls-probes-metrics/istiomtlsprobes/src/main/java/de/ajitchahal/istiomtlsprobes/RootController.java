package de.ajitchahal.istiomtlsprobes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
	@GetMapping("/")
	public String hello() {
		return "hello";
	}
}