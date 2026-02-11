package ec.edu.ups.icc.fundamentos01.schedules.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.schedules.services.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    
    private final ScheduleService service;

    public ScheduleController(ScheduleService service) { 
        this.service = service; 
    }

    @GetMapping("/my-slots")
    public List<String> getMySlots() {
        return service.findMySlots();
    }

    @PostMapping
    public void addSlot(@RequestBody Map<String, String> body) {
        service.addSlot(body.get("slot"));
    }

    @DeleteMapping
    public void removeSlot(@RequestParam("slot") String slot) {
        service.removeSlot(slot);
    }
}