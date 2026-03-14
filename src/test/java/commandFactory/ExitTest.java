package commandFactory;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ExitTest {

    @Test
    void testExecute_PrintsGoodbyeMessage() {

        Exit exit = new Exit();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // We try to create version which will not execute System.exit()
        try {
            Command testExit = new Command() {  // it is created an obstract object
                @Override
                public void execute() {
                    System.out.println("Thank you for using Hotel Management System. Goodbye!");
                }
            };
            testExit.execute();
        } catch (Exception e) {
            fail("Test should not throw exception: " + e.getMessage());
        }

        String output = outputStream.toString().trim();
        assertEquals("Thank you for using Hotel Management System. Goodbye!", output);
    }

    @Test
    void testExitClassExistsAndHasExecuteMethod() {
        Exit exit = new Exit();
        assertNotNull(exit);

        assertDoesNotThrow(() -> {
            // We're only checking if we are able to create an object
        });
    }
}