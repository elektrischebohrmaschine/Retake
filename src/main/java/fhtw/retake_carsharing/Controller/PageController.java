package fhtw.retake_carsharing.Controller;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import fhtw.retake_carsharing.Perisistence.Entities.User;
import fhtw.retake_carsharing.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PageController {

    private final UserService userService;

    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Optional<User> potUser = userService.login(username, password);
        if (potUser.isEmpty()) {
            model.addAttribute("error", "Username oder Passwort falsch");
            return "login";
        }
        session.setAttribute("token", potUser.get().getToken());
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        Optional<User> potUser = userService.authenticate(token);
        if (potUser.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("user", potUser.get());
        return "home";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        userService.logout((String) session.getAttribute("token"));
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("newUser", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Bitte alle Felder korrekt ausfüllen");
            return "register";
        }
        if (userService.signUp(newUser).isEmpty()) {
            model.addAttribute("error", "Username ist schon vergeben");
            return "register";
        }
        return "redirect:/";
    }
}
