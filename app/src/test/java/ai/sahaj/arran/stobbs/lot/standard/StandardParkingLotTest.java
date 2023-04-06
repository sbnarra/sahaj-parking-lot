package ai.sahaj.arran.stobbs.lot.standard;

import ai.sahaj.arran.stobbs.lot.FeeCalculator;
import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.lot.SpotManager;
import ai.sahaj.arran.stobbs.model.Receipt;
import ai.sahaj.arran.stobbs.model.Ticket;
import ai.sahaj.arran.stobbs.repository.TicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class StandardParkingLotTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private FeeCalculator feeCalculator;
    @Mock
    private SpotManager spotManager;

    @InjectMocks
    private StandardParkingLot parkingLot;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(ticketRepository, feeCalculator, spotManager);
    }

    @Test
    public void testPark() throws ParkingLotError {

        Mockito.when(spotManager.obtain("someVehicle"))
                .thenReturn(new SpotManager.Obtained(1, List.of()));

        Ticket ticket = parkingLot.park("someVehicle", LocalDateTime.MAX);

        Assertions.assertEquals(1, ticket.spotNumber());
        Assertions.assertEquals(Ticket.Status.PARKED, ticket.status());
        Assertions.assertEquals("someVehicle", ticket.vehicleType());

        Mockito.verify(ticketRepository).save(Mockito.any());
    }

    @Test
    public void testUnpark() throws ParkingLotError {

        String ticketNumber = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(ticketNumber, "someVehicle", 1, List.of(), LocalDateTime.MIN);

        Mockito.when(ticketRepository.find(ticketNumber)).thenReturn(ticket);

        Receipt receipt = parkingLot.unpark(ticketNumber, LocalDateTime.MAX);

        Assertions.assertEquals(Ticket.Status.PAID, receipt.ticket().status());

        Mockito.verify(ticketRepository).find(ticketNumber);
        Mockito.verify(feeCalculator).calculate(ticket, LocalDateTime.MAX);
        Mockito.verify(spotManager).release("someVehicle", 1);

    }
}
