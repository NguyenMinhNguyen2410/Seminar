package RFID;

import com.impinj.octane.*;


public class ReadTags {
    public static ImpinjReader reader = new ImpinjReader();
    public static void ReadTags() {

        try {
            // Pass in a reader hostname or IP address as a command line argument when running the example.
            String hostname = "169.254.96.13";//IP in website http://speedwayr-(MAC address device).local         user:root,password:impinj
            if (hostname == null) {
                throw new Exception("Wrong hostname");
            }
            // Connect to the reader.
            System.out.println("Connecting");
            reader.connect(hostname);

            // Get the default settings.
            // We'll use these as a starting point and then modify the settings we're interested in.
            Settings settings = reader.queryDefaultSettings();

            ReportConfig report = settings.getReport();

            // Tell the reader to include the antenna number in all tag reports. Other fields can be added
            // to the reports in the same way by setting the appropriate Report.IncludeXXXXXXX property.
            report.setIncludeAntennaPortNumber(true);
            report.setMode(ReportMode.Individual);

            // The reader can be set into various modes in which reader dynamics are optimized for
            // specific regions and environments.
            // The following mode, AutoSetDenseReaderDeepScan, monitors RF noise and interference
            // and then automatically and continuously optimizes the reader's configuration.
            settings.setRfMode(1002);
            settings.setSearchMode(SearchMode.DualTarget);
            settings.setSession(2);

            // Enable antenna #1. Disable all others.
            AntennaConfigGroup antennas = settings.getAntennas();
            antennas.disableAll();
            antennas.enableById(new short[]{1});

            // Set the Transmit Power and
            // Receive Sensitivity to the maximum.
            antennas.getAntenna((short) 1).setIsMaxRxSensitivity(false);
            antennas.getAntenna((short) 1).setIsMaxTxPower(false);

            // You can also set them to specific values like this...
             antennas.getAntenna((short) 1).setTxPowerinDbm(20.0);
             antennas.getAntenna((short) 1).setRxSensitivityinDbm(-70);

            // Apply the newly modified settings.
            System.out.println("Applying Settings");
            reader.applySettings(settings);

            // Assign the TagsReported event listener.
            // This specifies which object to inform when tags reports are available.
//            reader.setTagReportListener(new TagReportListenerImplementation());
        } catch (OctaneSdkException ex) {
            System.out.println("Octane SDK exception: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
