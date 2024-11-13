import controller.GatorTicketMaster;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <input_file>");
            System.exit(1);
        }

        String inputFile = args[0];
        GatorTicketMaster ticketMaster = new GatorTicketMaster();
        ticketMaster.processInputFile(inputFile);
    }
}