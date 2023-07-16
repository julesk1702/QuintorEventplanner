package nl.han.oose.clipper.clipperapi.domain.event.application.dto;

import java.util.List;

public class GuestWithDiets {

   private Guest guest;
   private List<Long> dietIds;

   public Guest getGuest() {
      return guest;
   }

   public void setGuest(Guest guest) {
      this.guest = guest;
   }

   public List<Long> getDietIds() {
      return dietIds;
   }

   public void setDietIds(List<Long> dietIds) {
      this.dietIds = dietIds;
   }
}
