/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.group51.servicedeskproject.model.Priority;
import com.group51.servicedeskproject.model.Status;
import com.group51.servicedeskproject.model.Ticket;
import com.group51.servicedeskproject.repository.InMemoryTicketRepository;
import com.group51.servicedeskproject.service.TicketService;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author kyvas
 */
public class TicketServiceTest {
    // Testing Ticket Service using memory
    // Test ticket creation
    @Test
    public void testCreateTicket() {
        TicketService service = new TicketService(new InMemoryTicketRepository());

        Ticket t = service.createTicket(1, "Login issue", "Cannot login", Priority.HIGH, "IT");

        assertEquals(1, t.getId(), 0.0001);
        assertEquals("Login issue", t.getTitle());
    }

    // Test update ticket status
    @Test
    public void testUpdateStatus() {
        TicketService service = new TicketService(new InMemoryTicketRepository());

        service.createTicket(1, "Test", "Desc", Priority.LOW, "IT");
        service.updateStatus(1, Status.RESOLVED);

        assertEquals(Status.RESOLVED, service.getTicketById(1).getStatus());
    }

    // Test update ticket priority
    @Test
    public void testUpdatePriority() {
        TicketService service = new TicketService(new InMemoryTicketRepository());

        service.createTicket(1, "Test", "Desc", Priority.LOW, "IT");
        service.updatePriority(1, Priority.HIGH);

        assertEquals(Priority.HIGH, service.getTicketById(1).getPriority());
    }
}
