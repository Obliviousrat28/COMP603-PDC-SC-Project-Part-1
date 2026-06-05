/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.group51.servicedeskproject.model.Priority;
import com.group51.servicedeskproject.model.Ticket;
import com.group51.servicedeskproject.repository.DerbyTicketRepository;
import database.DatabaseConnection;
import database.DatabaseInitializer;
import java.sql.Connection;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author kyvas
 */
public class DerbyTicketRepositoryTest {

    @Test
    public void testSaveAndFindTicket() throws Exception {
        DatabaseInitializer.init();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        DerbyTicketRepository repo = new DerbyTicketRepository(conn);

        Ticket t = new Ticket(10, "DB Test", "Testing", Priority.MEDIUM, "IT");

        repo.save(t);

        Ticket result = repo.findById(10);

        assertEquals("DB Test", result.getTitle());
    }

    @Test
    public void testUpdateTicket() throws Exception {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        DerbyTicketRepository repo = new DerbyTicketRepository(conn);

        Ticket t = new Ticket(11, "Old", "Old desc", Priority.LOW, "IT");

        repo.save(t);

        t.setPriority(Priority.HIGH);
        repo.update(t);

        Ticket updated = repo.findById(11);

        assertEquals(Priority.HIGH, updated.getPriority());
    }
}
