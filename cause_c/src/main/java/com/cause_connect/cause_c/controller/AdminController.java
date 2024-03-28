package com.cause_connect.cause_c.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cause_connect.cause_c.model.Admin;
import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.repo.CauseRepo;
import com.cause_connect.cause_c.repo.UserRepo;
import com.cause_connect.cause_c.service.AdminService;
import com.cause_connect.cause_c.service.UserService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CauseRepo crepo;

    @Autowired
    private UserRepo urepo;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "adminlogin"; // return the name of the admin login page view
    }

    @PostMapping("/login")
    public String loginAdmin(@ModelAttribute Admin admin, Model model, HttpSession session) {
        try {
            Admin loggedInAdmin = userService.loginAdmin(admin.getEmail(), admin.getPassword());
            if (loggedInAdmin != null) {
                session.setAttribute("admin", loggedInAdmin);
                return "adminfeature"; // return the name of the admin dashboard view
            } else {
                model.addAttribute("error", "You are not Admin, Try Login as User !!");
                List<Cause> causes = crepo.findAll();
                model.addAttribute("causes", causes);
                return "home"; // return the name of the admin login view
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Cause> causes = crepo.findAll();
            model.addAttribute("causes", causes);
            return "home";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/addcause")
    public String addCause(Model model) {
        model.addAttribute("cause", new Cause());
        return "adminadd";
    }

    @PostMapping("/addcause")
    public String addCause(@ModelAttribute Cause cause, @RequestParam("image") MultipartFile image, Model model,
            RedirectAttributes redirectAttributes) {
        if (!image.isEmpty()) {
            String imageName = cause.getName() + ".jpg";
            cause.setImageUrl(imageName);

            // Save the image file to the 'images' directory
            try {
                byte[] bytes = image.getBytes();
                Path path = Paths.get("Images/" + imageName);

                if (!Files.exists(path)) {
                    Files.createDirectories(path.getParent());
                }
                Files.write(path, bytes);

            } catch (IOException e) {
                // Handle the case where the image file could not be saved

                return "An error occurred while saving the image";
            }
        }
        String message = adminService.addCause(cause);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/admin/adminfeature";
    }

    @GetMapping("/deletecause")
    public String deleteCausePage(Model model) {
        List<Cause> causes = crepo.findAll();
        model.addAttribute("causes", causes);
        return "admindelete";
    }

    @PostMapping("/deletecause")
    public String deleteCause(@RequestParam int id, RedirectAttributes redirectAttributes) {
        String result = adminService.deleteCause(id);
        System.out.println(result);
        if (result.equals("Deleted!!")) {
            redirectAttributes.addFlashAttribute("message", "Cause deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cause not found");
        }
        return "redirect:/admin/adminfeature";
    }

    @GetMapping("/adminfeature")
    public String adminfeature() {
        return "adminfeature";
    }

    @GetMapping("/history")
    public String getAllUsersPaymentHistory(Model model) {
        List<Map<String, Object>> allUsersDonationHistory = adminService.getAllUsersPaymentHistory();

        if (!allUsersDonationHistory.isEmpty()) {
            model.addAttribute("allUsersDonationHistory", allUsersDonationHistory);
        } else {
            model.addAttribute("message", "No donations found !!");
        }
        return "adminhistory";
    }
    /*
     * @PostMapping("/addcause")
     * public String addCause(@RequestBody Cause cause) {
     * crepo.save(cause);
     * return "Cause created successfully!";
     * }
     * 
     * 
     * @DeleteMapping("/deletecause/{name}")
     * public String deleteCause(@PathVariable String name) {
     * Cause cause = crepo.findByName(name);
     * crepo.delete(cause);
     * return "Cause deleted successfully!";
     * }
     */

}
