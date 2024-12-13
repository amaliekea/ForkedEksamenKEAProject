package org.example.eksamenkea.controller;
import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.Role;
import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.service.EmployeeService;
import org.example.eksamenkea.service.Errorhandling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("")
@Controller
public class EmployeeController { //Amalie

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        if (session.getAttribute("employee") != null) { //tjekker om en bruger er logget ind
            model.addAttribute("employeeAvaliable", true); //angiver at brugeren er tilgængelig
            model.addAttribute("employee", session.getAttribute("employee"));
        } else {
            model.addAttribute("employeeAvaliable", false);
        }
        return "homepage";
    }

    @PostMapping("/validate_login")
    public String validateLogin(HttpSession session, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            Employee employee = employeeService.signIn(email, password); // hent den specifikke medarbejder
            if (employee != null) {
                session.setAttribute("employee", employee);// Gem medarbejderen i sessionen
                session.setAttribute("userRole", employee.getRole());
                session.setAttribute("employeeId", employee.getEmployeeId());
                session.setMaxInactiveInterval(1800); // set session til maks 30 minutter
                return "redirect:/logged_in";
            } else {
                model.addAttribute("errorMessage", "Wrong email or password.");
                return "login";
            }
        } catch (Errorhandling e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logged_in")
    public String loggedIn(HttpSession session) throws Exception {
        Employee employee = (Employee) session.getAttribute("employee");  // Henter "employee" fra sessionen.
        if (employee == null) {
            return "redirect:/login";
        }
        if (employee.getRole() == Role.PROJECTLEADER) {
            return "redirect:/project-leader-overview";
        } else if (employee.getRole() == Role.WORKER) {
            return "redirect:/worker-overview";
        }
        throw new Errorhandling("no role found");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); //invaliderer sessionen for at logge ud
        return "redirect:/"; //return til front pagen
    }

}