package ec.edu.ups.icc.fundamentos01.schedules.services;

import java.util.List;

public interface ScheduleService {
    
    List<String> findMySlots();
    void addSlot(String slot);
    void removeSlot(String slot);
}