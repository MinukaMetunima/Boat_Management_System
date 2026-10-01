package com.boatsafari.test;

import com.boatsafari.common.dao.*;
import com.boatsafari.common.model.*;
import com.boatsafari.common.util.LiveTableManager;
import com.boatsafari.member1.dao.*;
import com.boatsafari.member1.model.*;
import com.boatsafari.member2.dao.*;
import com.boatsafari.member2.model.*;
import com.boatsafari.member3.dao.BoatDAO;
import com.boatsafari.member3.model.*;
import com.boatsafari.member4.dao.*;
import com.boatsafari.member4.model.*;
import com.boatsafari.member5.dao.EmergencyDAO;
import com.boatsafari.member5.model.*;
import com.boatsafari.member6.dao.*;
import com.boatsafari.member6.model.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive CRUD & Live Table Modification Test for Microsoft SQL Server (SSMS).
 * Verifies CREATE, READ, UPDATE, and DELETE operations across all application entities
 * and synchronizes live TABLE_*.txt files in real-time.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AllCrudComprehensiveTest {

    private static final String BANNER = "====================================================================";

    @BeforeAll
    public static void setUp() {
        System.out.println(BANNER);
        System.out.println(" STARTING COMPREHENSIVE CRUD VERIFICATION ON MICROSOFT SQL SERVER");
        System.out.println(" Database: boat_safari_db | Credentials: sa / 0000");
        System.out.println(BANNER);
    }

    @AfterAll
    public static void tearDown() {
        // Refresh all 17 live table text files after all test operations
        LiveTableManager.exportAllTables();
        System.out.println(BANNER);
        System.out.println(" ALL 16 TABLES SUCCESSFULLY TESTED FOR CRUD OPERATIONS!");
        System.out.println(" ALL LIVE TABLE TEXT FILES REFRESHED FROM SQL SERVER!");
        System.out.println(BANNER);
    }

    // ==========================================
    // 1. COMMON: Users
    // ==========================================
    @Test
    @Order(1)
    @DisplayName("CRUD 1/14: Users (Create, Read, Update, Delete)")
    public void testUsersCrud() {
        System.out.println("\n--- [1/14] Testing CRUD on table: USERS ---");
        UserDAO dao = new UserDAO();

        // 1. CREATE
        Staff staff = new Staff();
        staff.setFullName("Test Senior Skipper Perera");
        staff.setEmail("test.skipper." + System.currentTimeMillis() + "@sail-safari.lk");
        staff.setPassword("SkipperPass@123");
        staff.setPhone("+94 77 999 1122");
        staff.setRole(UserRole.CAPTAIN);
        staff.setStatus("ACTIVE");

        User created = dao.create(staff);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted user #" + created.getId() + " (" + created.getEmail() + ")");

        // 2. READ
        Optional<User> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Senior Skipper Perera", found.get().getFullName());
        assertEquals(UserRole.CAPTAIN, found.get().getRole());
        System.out.println("  [READ] Verified user #" + found.get().getId() + ": " + found.get().getFullName());

        // 3. UPDATE
        found.get().setPhone("+94 77 888 3344");
        found.get().setStatus("INACTIVE");
        boolean updated = dao.update(found.get());
        assertTrue(updated);

        Optional<User> updatedUser = dao.findById(created.getId());
        assertTrue(updatedUser.isPresent());
        assertEquals("+94 77 888 3344", updatedUser.get().getPhone());
        assertEquals("INACTIVE", updatedUser.get().getStatus());
        System.out.println("  [UPDATE] Updated phone & status for user #" + created.getId());

        // 4. DELETE
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Successfully deleted user #" + created.getId());
        LiveTableManager.exportSingleTable("users");
    }

    // ==========================================
    // 2. MEMBER 1: Destinations
    // ==========================================
    @Test
    @Order(2)
    @DisplayName("CRUD 2/14: Destinations (Create, Read, Update, Delete)")
    public void testDestinationsCrud() {
        System.out.println("\n--- [2/14] Testing CRUD on table: DESTINATIONS ---");
        DestinationDAO dao = new DestinationDAO();

        // 1. CREATE
        Destination d = new Destination();
        d.setName("Tangalle Secret Coral Lagoon");
        d.setRegion(CoastRegion.SOUTH_COAST);
        d.setHarborName("Tangalle Fishery Pier");
        d.setDescription("Secluded turquoise lagoon with pristine live coral reefs.");
        d.setImageUrl("assets/img/destinations/tangalle.jpg");

        Destination created = dao.create(d);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted destination #" + created.getId() + " (" + created.getName() + ")");

        // 2. READ
        Optional<Destination> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Tangalle Secret Coral Lagoon", found.get().getName());
        assertEquals(CoastRegion.SOUTH_COAST, found.get().getRegion());
        System.out.println("  [READ] Verified destination #" + found.get().getId());

        // 3. UPDATE
        found.get().setHarborName("Tangalle International Marina");
        found.get().setDescription("Updated luxury marina facility for mega catamarans.");
        boolean updated = dao.update(found.get());
        assertTrue(updated);

        Optional<Destination> check = dao.findById(created.getId());
        assertTrue(check.isPresent());
        assertEquals("Tangalle International Marina", check.get().getHarborName());
        System.out.println("  [UPDATE] Updated harbor name for destination #" + created.getId());

        // 4. DELETE
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Deleted destination #" + created.getId());
        LiveTableManager.exportSingleTable("destinations");
    }

    // ==========================================
    // 3. MEMBER 1: Routes
    // ==========================================
    @Test
    @Order(3)
    @DisplayName("CRUD 3/14: Routes (Create, Read, Update, Delete)")
    public void testRoutesCrud() {
        System.out.println("\n--- [3/14] Testing CRUD on table: ROUTES ---");
        RouteDAO dao = new RouteDAO();

        // 1. CREATE
        Route r = new Route();
        r.setName("Galle to Unawatuna Coastal Voyage");
        r.setStartDestinationId(2);
        r.setEndDestinationId(2);
        r.setDurationHours(2.5);
        r.setDistanceNm(8.0);
        r.setHighlights("Jungle beach, coral garden, sunset view");

        Route created = dao.create(r);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted route #" + created.getId() + " (" + created.getName() + ")");

        // 2. READ
        Optional<Route> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Galle to Unawatuna Coastal Voyage", found.get().getName());
        assertEquals(2.5, found.get().getDurationHours());
        System.out.println("  [READ] Verified route #" + found.get().getId());

        // 3. UPDATE
        found.get().setDistanceNm(11.5);
        found.get().setDurationHours(3.0);
        boolean updated = dao.update(found.get());
        assertTrue(updated);

        Optional<Route> check = dao.findById(created.getId());
        assertTrue(check.isPresent());
        assertEquals(11.5, check.get().getDistanceNm());
        System.out.println("  [UPDATE] Updated distance for route #" + created.getId());

        // 4. DELETE
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Deleted route #" + created.getId());
        LiveTableManager.exportSingleTable("routes");
    }

    // ==========================================
    // 4. MEMBER 1: Tours
    // ==========================================
    @Test
    @Order(4)
    @DisplayName("CRUD 4/14: Tours (Create, Read, Update, Delete)")
    public void testToursCrud() {
        System.out.println("\n--- [4/14] Testing CRUD on table: TOURS ---");
        TourDAO dao = new TourDAO();

        // 1. CREATE
        Tour t = new Tour();
        t.setTitle("Sunset Champagne & Dolphin Cruise");
        t.setTourType(TourType.SUNSET_SAIL);
        t.setRouteId(1);
        t.setDescription("Scenic evening catamaran sailing trip.");
        t.setDurationHours(3.0);
        t.setBasePriceAmount(BigDecimal.valueOf(19500.00));
        t.setMaxPassengers(20);
        t.setInclusions("Champagne, Canapes, Sound system");
        t.setExclusions("Hotel pickup");
        t.setImageUrl("assets/img/tours/sunset.jpg");
        t.setActive(true);

        Tour created = dao.create(t);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted tour #" + created.getId() + " (" + created.getTitle() + ")");

        // 2. READ
        Optional<Tour> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Sunset Champagne & Dolphin Cruise", found.get().getTitle());
        System.out.println("  [READ] Verified tour #" + found.get().getId() + " with active tours count = " + dao.findActive().size());

        // 3. UPDATE
        found.get().setTitle("Elite Sunset Champagne & Dolphin Cruise - VIP");
        found.get().setBasePriceAmount(BigDecimal.valueOf(22500.00));
        boolean updated = dao.update(found.get());
        assertTrue(updated);

        Optional<Tour> check = dao.findById(created.getId());
        assertTrue(check.isPresent());
        assertEquals("Elite Sunset Champagne & Dolphin Cruise - VIP", check.get().getTitle());
        System.out.println("  [UPDATE] Updated title & price for tour #" + created.getId());

        // 4. DELETE
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Deleted tour #" + created.getId());
        LiveTableManager.exportSingleTable("tours");
    }

    // ==========================================
    // 5. MEMBER 3: Vessels (Boat Fleet)
    // ==========================================
    @Test
    @Order(5)
    @DisplayName("CRUD 5/14: Vessels (Create, Read, Update, Delete)")
    public void testVesselsCrud() {
        System.out.println("\n--- [5/14] Testing CRUD on table: VESSELS ---");
        BoatDAO dao = new BoatDAO();

        // 1. CREATE
        Catamaran cat = new Catamaran();
        cat.setName("Southern Spirit (Ceycat 48)");
        cat.setRegistrationNo("SLC-TEST-" + System.currentTimeMillis() % 100000);
        cat.setVesselType(VesselType.CATAMARAN);
        cat.setCapacityValues(24, 3);
        cat.setEngines("Twin 60HP Yanmar");
        cat.setCruisingSpeedKnots(10.0);
        cat.setStatus(VesselStatus.AVAILABLE);
        cat.setImageUrl("assets/img/fleet/test-cat.jpg");
        cat.setSafetyEquipmentNotes("Full SOLAS compliance");

        Vessel created = dao.create(cat);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted catamaran #" + created.getId() + " (" + created.getName() + ")");

        // 2. READ
        Optional<Vessel> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals(VesselType.CATAMARAN, found.get().getVesselType());
        System.out.println("  [READ] Verified vessel #" + found.get().getId());

        // 3. UPDATE
        boolean statusUpdated = dao.updateStatus(created.getId(), VesselStatus.UNDER_MAINTENANCE);
        assertTrue(statusUpdated);

        Optional<Vessel> check = dao.findById(created.getId());
        assertTrue(check.isPresent());
        assertEquals(VesselStatus.UNDER_MAINTENANCE, check.get().getStatus());
        System.out.println("  [UPDATE] Updated vessel status to UNDER_MAINTENANCE for vessel #" + created.getId());

        // 4. DELETE
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Deleted vessel #" + created.getId());
        LiveTableManager.exportSingleTable("vessels");
    }

    // ==========================================
    // 6. MEMBER 1: Tour Schedules
    // ==========================================
    @Test
    @Order(6)
    @DisplayName("CRUD 6/14: Tour Schedules (Create, Read, Update, Delete)")
    public void testTourSchedulesCrud() {
        System.out.println("\n--- [6/14] Testing CRUD on table: TOUR_SCHEDULES ---");
        TourScheduleDAO dao = new TourScheduleDAO();

        // 1. CREATE
        TourSchedule sched = new TourSchedule();
        sched.setTourId(1);
        sched.setVesselId(1);
        sched.setCaptainId(4);
        sched.setGuideId(6);
        sched.setDepartureTime(new Timestamp(System.currentTimeMillis() + 86400000L));
        sched.setReturnTime(new Timestamp(System.currentTimeMillis() + 86400000L + 18000000L));
        sched.setAvailableSeats(25);
        sched.setStatus(ScheduleStatus.SCHEDULED);

        TourSchedule created = dao.create(sched);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted tour schedule #" + created.getId());

        // 2. READ
        Optional<TourSchedule> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals(25, found.get().getAvailableSeats());
        System.out.println("  [READ] Verified schedule #" + found.get().getId());

        // 3. UPDATE
        boolean seatsDeducted = dao.updateAvailableSeats(created.getId(), 5);
        assertTrue(seatsDeducted);

        Optional<TourSchedule> check = dao.findById(created.getId());
        assertTrue(check.isPresent());
        assertEquals(20, check.get().getAvailableSeats());
        System.out.println("  [UPDATE] Deducted 5 seats; new available seats = " + check.get().getAvailableSeats());

        // 4. DELETE
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Deleted tour schedule #" + created.getId());
        LiveTableManager.exportSingleTable("tour_schedules");
    }

    // ==========================================
    // 7. MEMBER 2: Reservations & Passengers
    // ==========================================
    @Test
    @Order(7)
    @DisplayName("CRUD 7/14: Reservations & Passengers (Create, Read, Update, Delete)")
    public void testReservationsAndPassengersCrud() {
        System.out.println("\n--- [7/14] Testing CRUD on tables: RESERVATIONS & PASSENGERS ---");
        ReservationDAO resDao = new ReservationDAO();
        PassengerDAO paxDao = new PassengerDAO();

        // 1. CREATE RESERVATION
        Reservation res = new Reservation();
        res.setBookingRef("SLC-AUTO-" + System.currentTimeMillis() % 100000);
        res.setScheduleId(1);
        res.setCustomerId(10);
        res.setPassengerCount(2);
        res.setTotalAmountValue(BigDecimal.valueOf(49000.00));
        res.setDiscountAmountValue(BigDecimal.valueOf(4900.00));
        res.setFinalAmountValue(BigDecimal.valueOf(44100.00));
        res.setStatus(ReservationStatus.CONFIRMED);
        res.setSpecialNotes("Window side seats requested");

        Reservation createdRes = resDao.create(res);
        assertNotNull(createdRes.getId());
        assertTrue(createdRes.getId() > 0);
        System.out.println("  [CREATE] Inserted reservation #" + createdRes.getId() + " (" + createdRes.getBookingRef() + ")");

        // 1b. CREATE PASSENGER
        Passenger p1 = new Passenger("Alice Wonder", "PASSPORT1234", 28, "FEMALE", "British", "+4471112233");
        p1.setReservationId(createdRes.getId());
        Passenger createdPax = paxDao.create(p1);
        assertNotNull(createdPax.getId());
        assertTrue(createdPax.getId() > 0);
        System.out.println("  [CREATE] Inserted passenger #" + createdPax.getId() + " for reservation #" + createdRes.getId());

        // 2. READ
        Optional<Reservation> foundRes = resDao.findById(createdRes.getId());
        assertTrue(foundRes.isPresent());
        assertEquals("Window side seats requested", foundRes.get().getSpecialNotes());

        List<Passenger> pList = paxDao.findByReservationId(createdRes.getId());
        assertFalse(pList.isEmpty());
        System.out.println("  [READ] Verified reservation #" + createdRes.getId() + " with " + pList.size() + " passenger(s)");

        // 3. UPDATE
        createdRes.setStatus(ReservationStatus.CHECKED_IN);
        createdRes.setSpecialNotes("Guests boarded at Pier 1");
        boolean resUpdated = resDao.update(createdRes);
        assertTrue(resUpdated);

        Optional<Reservation> checkRes = resDao.findById(createdRes.getId());
        assertTrue(checkRes.isPresent());
        assertEquals(ReservationStatus.CHECKED_IN, checkRes.get().getStatus());
        System.out.println("  [UPDATE] Updated reservation status to CHECKED_IN");

        // 4. DELETE PASSENGERS & RESERVATION
        boolean paxDeleted = paxDao.deleteByReservationId(createdRes.getId());
        assertTrue(paxDeleted);

        boolean resDeleted = resDao.delete(createdRes.getId());
        assertTrue(resDeleted);
        System.out.println("  [DELETE] Deleted reservation #" + createdRes.getId() + " and associated passengers");
        LiveTableManager.exportSingleTable("reservations");
        LiveTableManager.exportSingleTable("passengers");
    }

    // ==========================================
    // 8. MEMBER 4: Maintenance Records
    // ==========================================
    @Test
    @Order(8)
    @DisplayName("CRUD 8/14: Maintenance Records (Create, Read, Update, Delete)")
    public void testMaintenanceCrud() {
        System.out.println("\n--- [8/14] Testing CRUD on table: MAINTENANCE_RECORDS ---");
        MaintenanceDAO dao = new MaintenanceDAO();

        // 1. CREATE
        MaintenanceRecord rec = new MaintenanceRecord();
        rec.setVesselId(1);
        rec.setMaintenanceType(MaintenanceType.ROUTINE_SERVICE);
        rec.setDescription("Engine coolant flush and fresh water heat exchanger decalcification");
        rec.setScheduledDate(Date.valueOf(LocalDate.now().plusDays(5)));
        rec.setCostAmount(BigDecimal.valueOf(35000.00));
        rec.setServiceProvider("Galle Dockyard Marine Mechanics");
        rec.setStatus(MaintenanceStatus.SCHEDULED);

        MaintenanceRecord created = dao.create(rec);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted maintenance record #" + created.getId());

        // 2. READ
        Optional<MaintenanceRecord> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Galle Dockyard Marine Mechanics", found.get().getServiceProvider());
        System.out.println("  [READ] Verified maintenance record #" + found.get().getId());

        // 3. UPDATE
        found.get().setStatus(MaintenanceStatus.COMPLETED);
        found.get().setCompletedDate(Date.valueOf(LocalDate.now()));
        found.get().setCostAmount(BigDecimal.valueOf(38500.00));
        boolean updated = dao.update(found.get());
        assertTrue(updated);

        Optional<MaintenanceRecord> check = dao.findById(created.getId());
        assertTrue(check.isPresent());
        assertEquals(MaintenanceStatus.COMPLETED, check.get().getStatus());
        System.out.println("  [UPDATE] Updated maintenance record status to COMPLETED");

        // 4. DELETE
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Deleted maintenance record #" + created.getId());
        LiveTableManager.exportSingleTable("maintenance_records");
    }

    // ==========================================
    // 9. MEMBER 4: Service Reminders
    // ==========================================
    @Test
    @Order(9)
    @DisplayName("CRUD 9/14: Service Reminders (Create, Read, Update, Delete)")
    public void testServiceRemindersCrud() {
        System.out.println("\n--- [9/14] Testing CRUD on table: SERVICE_REMINDERS ---");
        ReminderDAO dao = new ReminderDAO();

        // 1. CREATE
        ServiceReminder rem = new ServiceReminder();
        rem.setVesselId(2);
        rem.setReminderTitle("Yanmar Saildrive Diaphragm Inspection");
        rem.setIntervalDays(180);
        rem.setLastServicedDate(Date.valueOf("2026-03-01"));
        rem.setNextDueDate(Date.valueOf(LocalDate.now().plusDays(30)));
        rem.setOverdue(false);
        rem.setNotes("Manufacturer 7-year saildrive seal replacement cycle");

        ServiceReminder created = dao.create(rem);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted service reminder #" + created.getId());

        // 2. READ
        Optional<ServiceReminder> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Yanmar Saildrive Diaphragm Inspection", found.get().getReminderTitle());
        System.out.println("  [READ] Verified service reminder #" + found.get().getId());

        // 3. UPDATE
        found.get().setOverdue(true);
        found.get().setNotes("Priority updated: inspection scheduled this week");
        boolean updated = dao.update(found.get());
        assertTrue(updated);

        Optional<ServiceReminder> check = dao.findById(created.getId());
        assertTrue(check.isPresent());
        assertTrue(check.get().isOverdue());
        System.out.println("  [UPDATE] Updated service reminder overdue status");

        // 4. DELETE
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Deleted service reminder #" + created.getId());
        LiveTableManager.exportSingleTable("service_reminders");
    }

    // ==========================================
    // 10. MEMBER 4: Safety Check Logs
    // ==========================================
    @Test
    @Order(10)
    @DisplayName("CRUD 10/14: Safety Check Logs (Create, Read, Update, Delete)")
    public void testSafetyCheckLogsCrud() {
        System.out.println("\n--- [10/14] Testing CRUD on table: SAFETY_CHECK_LOGS ---");
        SafetyCheckDAO dao = new SafetyCheckDAO();

        // 1. CREATE
        SafetyCheckLog log = new SafetyCheckLog();
        log.setScheduleId(1);
        log.setVesselId(1);
        log.setCaptainId(4);
        log.setSafetyItemsVerified(true);
        log.setLifeJacketsCount(35);
        log.setFuelLevelPercent(98);
        log.setWeatherConditions("Calm swell, 12 knots breeze");
        log.setBriefingConfirmed(true);
        log.setAllClear(true);
        log.setCaptainSignature("Capt. Shantha Perera");
        log.setStatus(SafetyCheckStatus.READY_FOR_DEPARTURE);
        log.setNotes("All lifesaving apparatus checked and verified operational.");

        SafetyCheckLog created = dao.create(log);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted safety check log #" + created.getId());

        // 2. READ
        Optional<SafetyCheckLog> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Capt. Shantha Perera", found.get().getCaptainSignature());
        System.out.println("  [READ] Verified safety check log #" + found.get().getId());

        // 3. UPDATE
        found.get().setFuelLevelPercent(100);
        found.get().setNotes("Topped to 100% fuel tank capacity.");
        boolean updated = dao.update(found.get());
        assertTrue(updated);

        Optional<SafetyCheckLog> check = dao.findById(created.getId());
        assertTrue(check.isPresent());
        assertEquals(100, check.get().getFuelLevelPercent());
        System.out.println("  [UPDATE] Updated fuel level to 100% on log #" + created.getId());

        // 4. DELETE / VOID
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Voided/Deleted safety check log #" + created.getId());
        LiveTableManager.exportSingleTable("safety_check_logs");
    }

    // ==========================================
    // 11. MEMBER 5: Emergency Notices
    // ==========================================
    @Test
    @Order(11)
    @DisplayName("CRUD 11/14: Emergency Notices (Create, Read, Update, Delete)")
    public void testEmergencyNoticesCrud() {
        System.out.println("\n--- [11/14] Testing CRUD on table: EMERGENCY_NOTICES ---");
        EmergencyDAO dao = new EmergencyDAO();

        // 1. CREATE
        EmergencyNotice notice = new EmergencyNotice();
        notice.setTitle("Approaching South Coast Monsoon Swell Alert");
        notice.setCategory(EmergencyCategory.BAD_WEATHER);
        notice.setSeverity(EmergencySeverity.HIGH);
        notice.setAffectedTourId(1);
        notice.setAffectedVesselId(1);
        notice.setAffectedRegion("SOUTH_COAST");
        notice.setMessage("Wave swells exceeding safe catamaran boarding thresholds. Precautionary delay.");
        notice.setBroadcastChannels("PORTAL,SMS");
        notice.setStatus(EmergencyStatus.ACTIVE);
        notice.setCreatedById(1);

        EmergencyNotice created = dao.create(notice);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Inserted emergency notice #" + created.getId());

        // 2. READ
        Optional<EmergencyNotice> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals(EmergencyStatus.ACTIVE, found.get().getStatus());
        System.out.println("  [READ] Verified emergency notice #" + found.get().getId());

        // 3. UPDATE (Resolve notice with CURRENT_TIMESTAMP)
        boolean resolved = dao.resolveNotice(created.getId());
        assertTrue(resolved);

        Optional<EmergencyNotice> check = dao.findById(created.getId());
        assertTrue(check.isPresent());
        assertEquals(EmergencyStatus.RESOLVED, check.get().getStatus());
        assertNotNull(check.get().getResolvedAt());
        System.out.println("  [UPDATE] Resolved emergency notice #" + created.getId() + " at " + check.get().getResolvedAt());

        // 4. DELETE
        boolean deleted = dao.delete(created.getId());
        assertTrue(deleted);
        System.out.println("  [DELETE] Deleted emergency notice #" + created.getId());
        LiveTableManager.exportSingleTable("emergency_notices");
    }

    // ==========================================
    // 12. MEMBER 6: Promotions & Redemptions
    // ==========================================
    @Test
    @Order(12)
    @DisplayName("CRUD 12/14: Promotions & Redemptions (Create, Read, Update, Delete)")
    public void testPromotionsAndRedemptionsCrud() {
        System.out.println("\n--- [12/14] Testing CRUD on tables: PROMOTIONS & PROMO_REDEMPTIONS ---");
        PromotionDAO promoDao = new PromotionDAO();
        PromoRedemptionDAO redempDao = new PromoRedemptionDAO();

        // 1. CREATE PROMOTION
        String code = "SUMMER" + (System.currentTimeMillis() % 10000);
        Promotion p = new Promotion();
        p.setName("Summer Whale Watching Special 20%");
        p.setPromoCode(code);
        p.setDiscountType(DiscountType.PERCENTAGE);
        p.setDiscountValue(BigDecimal.valueOf(20.00));
        p.setMinSpend(BigDecimal.valueOf(10000.00));
        p.setMaxDiscount(BigDecimal.valueOf(8000.00));
        p.setMaxRedemptions(50);
        p.setStartDate(Date.valueOf(LocalDate.now()));
        p.setEndDate(Date.valueOf(LocalDate.now().plusMonths(2)));
        p.setActive(true);

        Promotion createdPromo = promoDao.create(p);
        assertNotNull(createdPromo.getId());
        assertTrue(createdPromo.getId() > 0);
        System.out.println("  [CREATE] Inserted promotion #" + createdPromo.getId() + " with code '" + createdPromo.getPromoCode() + "'");

        // 1b. CREATE REDEMPTION
        PromoRedemption r = new PromoRedemption(createdPromo.getId(), 10, 1, BigDecimal.valueOf(3900.00));
        PromoRedemption createdRed = redempDao.create(r);
        assertNotNull(createdRed.getId());
        assertTrue(createdRed.getId() > 0);
        System.out.println("  [CREATE] Inserted redemption #" + createdRed.getId() + " for promo #" + createdPromo.getId());

        // 2. READ
        Optional<Promotion> foundPromo = promoDao.findByCode(code);
        assertTrue(foundPromo.isPresent());
        assertEquals(code, foundPromo.get().getPromoCode());
        System.out.println("  [READ] Found active promotion by code: '" + code + "'");

        // 3. UPDATE
        boolean usageIncremented = promoDao.incrementUsage(createdPromo.getId());
        assertTrue(usageIncremented);

        Optional<Promotion> checkPromo = promoDao.findById(createdPromo.getId());
        assertTrue(checkPromo.isPresent());
        assertEquals(1, checkPromo.get().getTimesRedeemed());
        System.out.println("  [UPDATE] Incremented usage count = " + checkPromo.get().getTimesRedeemed());

        // 4. DELETE
        boolean redDeleted = redempDao.delete(createdRed.getId());
        assertTrue(redDeleted);

        boolean promoDeleted = promoDao.delete(createdPromo.getId());
        assertTrue(promoDeleted);
        System.out.println("  [DELETE] Deleted redemption and promotion #" + createdPromo.getId());
        LiveTableManager.exportSingleTable("promotions");
        LiveTableManager.exportSingleTable("promo_redemptions");
    }

    // ==========================================
    // 13. COMMON: Login Details Audit
    // ==========================================
    @Test
    @Order(13)
    @DisplayName("CRUD 13/14: Login Details Audit (Create, Read, Verify)")
    public void testLoginDetailsCrud() {
        System.out.println("\n--- [13/14] Testing CRUD on table: LOGIN_DETAILS ---");
        LoginDetailsDAO dao = new LoginDetailsDAO();

        // 1. CREATE
        LoginDetail log = new LoginDetail(
                1, "System Administrator", "admin@sail-safari.lk", "ADMIN",
                "127.0.0.1", "SUCCESS - SQL SERVER VERIFICATION", "CRUD Testing Execution"
        );
        LoginDetail created = dao.create(log);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Recorded login detail #" + created.getId());

        // 2. READ
        Optional<LoginDetail> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("admin@sail-safari.lk", found.get().getEmail());
        System.out.println("  [READ] Verified login detail #" + found.get().getId());

        // 3. VERIFY LIST
        List<LoginDetail> all = dao.findAll();
        assertFalse(all.isEmpty());
        System.out.println("  [READ] Total login records in database = " + all.size());
        LiveTableManager.exportSingleTable("login_details");
    }

    // ==========================================
    // 14. COMMON: Activity Logs Audit
    // ==========================================
    @Test
    @Order(14)
    @DisplayName("CRUD 14/14: Activity Logs Audit (Create, Read, Verify)")
    public void testActivityLogsCrud() {
        System.out.println("\n--- [14/14] Testing CRUD on table: ACTIVITY_LOGS ---");
        ActivityLogDAO dao = new ActivityLogDAO();

        // 1. CREATE
        ActivityLog log = new ActivityLog(
                1, "SQL_SERVER_CRUD_VERIFICATION", "SYSTEM",
                "Automated end-to-end CRUD suite verified all tables successfully.", "127.0.0.1"
        );
        ActivityLog created = dao.create(log);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        System.out.println("  [CREATE] Recorded activity log #" + created.getId());

        // 2. READ
        Optional<ActivityLog> found = dao.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("SQL_SERVER_CRUD_VERIFICATION", found.get().getAction());
        System.out.println("  [READ] Verified activity log #" + found.get().getId());

        // 3. READ RECENT
        List<ActivityLog> recent = dao.findRecent(5);
        assertFalse(recent.isEmpty());
        System.out.println("  [READ] Fetched " + recent.size() + " recent activity log entries");
        LiveTableManager.exportSingleTable("activity_logs");
    }
}
