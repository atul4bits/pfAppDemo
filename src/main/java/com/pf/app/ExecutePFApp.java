package com.pf.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

import static com.pf.app.Constants.*;
import static java.lang.System.exit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
@EnableAutoConfiguration
public class ExecutePFApp {
    private static final Logger logger = LoggerFactory.getLogger(ExecutePFApp.class);

    @Autowired
    private ReadInput readInput;

    @Autowired
    private WriteOutput writeOutput;

    @Autowired
    private PFCache pfCache;

    @Autowired
    private PrimeFactorImpl primeFactor;

    @Autowired
    private Validation validation;

    private int invalidInputCount = 0;

    public void startExecution(){
        logger.debug(" -------  Execution Started -----------");
            displayInitialInput();
            String input = readInput.readInputFromConsole();

            if(input.equalsIgnoreCase(Constants.INPUT_FILE_CHAR)){
                logger.debug(" -------  File Input Selected -----------");
                handleFileInput();
            }else if(input.equalsIgnoreCase(Constants.INPUT_EXIT_CHAR) ){
                logger.debug(" -------  Exit Input Selected -----------");
                handleExit();
            }else{
                logger.debug(" -------  Invalid Input Selected -----------");
                // this will exit the system after three invalid attempts
                handleInvalidInput(Constants.NOT_VALID_INPUT);
                startExecution();
            }
        }

    private void handleExit() {
        writeOutput.printOnConsole(PRINT_1_LINE,YOU_CHOSE_CLOSE_APP,PRINT_1_LINE) ;
        exitTheApp();
    }

    private void handleFileInput() {
        String filePath;
        writeOutput.printOnConsole(PRiNT_2_LINE,Constants.BANNER_HASH, PRiNT_2_LINE);
        writeOutput.printOnConsole(Constants.ENTER_PATH_FILE) ;

        filePath = readInput.readInputFromConsole();

        logger.debug(" filePath: "+ filePath);
        if(!validateFile(filePath)) {
            // this will exit the system after three invalid attempts
            handleInvalidInput("");
            handleFileInput();
        }else{
            logger.debug(" valid file, starting to read the file   ");
            try {
                processPF(filePath);
            }catch (UDCheckedException udce){
                writeOutput.printOnConsole(1,udce.getMessage(),1);
            }
            // in case we wish to ask user to input another file, execution can be started with some more inputs
            // cache will come handy as we don't have to compute FPs again for numbers re-occurring in the input
            //startExecution();
                writeOutput.printOnConsole(PRiNT_2_LINE,Constants.APP_CLOSING,PRiNT_2_LINE);
        }
    }

    private void handleInvalidInput(String message){
        invalidInputCount++;
        writeOutput.printOnConsole(PRINT_1_LINE,message,PRINT_1_LINE);
        if(invalidInputCount == INVALID_INPUT_COUNT ){
            logger.debug(" Invalid Cound: "+ invalidInputCount);
            exitOnInvalidIPLimit();
        }
    }

    private void exitOnInvalidIPLimit() {
        writeOutput.printOnConsole(PRINT_1_LINE,Constants.KEYED_WRONG_INPUT_EXIT_APP,PRINT_1_LINE);
        logger.debug(" Invalid input limit reached, exiting the app ");
        exitTheApp();
    }

    private  void exitTheApp() {
        writeOutput.printOnConsole(PRiNT_2_LINE,Constants.APP_CLOSING,PRiNT_2_LINE);
        exit(0);
    }

    private boolean validateFile(String input) {
        File file = new File(input);
        if(!file.exists()) {
            logger.debug(" file doestn exit in the path provided");
            writeOutput.printOnConsole(PRINT_1_LINE,FILE_NOT_EXISTS,PRINT_1_LINE);
            return false;
        }else if(!file.canRead()){
            logger.debug(" system doesnt have read permissions to operate on file ");
            writeOutput.printOnConsole(PRINT_1_LINE,READ_PERMISSIONS,PRINT_1_LINE);
            return false;
        }else if(file.length() == 0){
            logger.debug(" File has nothing to read ");
            writeOutput.printOnConsole(PRINT_1_LINE,FILE_EMPTY,PRINT_1_LINE);
            return false;
        }
        // we can also check for if file is txt file only or not. If its a binary file we can throw relevant error/msg or typeos text files like .txt, .in, .out etc

        return true;
    }

    private void displayInitialInput(){
        writeOutput.addNewLines(PRINT_1_LINE);
        writeOutput.printOnConsole(START_THE_APP );
        writeOutput.addNewLines(PRiNT_2_LINE);
        writeOutput.printOnConsole(OPTION_1);
        writeOutput.addNewLines(PRINT_1_LINE);
        writeOutput.printOnConsole(OPTION_2);
        writeOutput.addNewLines(PRiNT_2_LINE);
        writeOutput.printOnConsole(ENTER_INPUT);

    }

    private void processPF(String filePath) throws UDCheckedException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            writeOutput.printOnConsole(PRINT_1_LINE, PRIME_FACTOR_LIST, PRiNT_2_LINE);
            while ((line = br.readLine()) != null) {
                int val = validation.validateIsInteger(line);
                if (val > 0) {
                    List<Integer> pfList = pfCache.getPFByNum(val);
                    if (pfList == null) {
                        logger.debug(" new element");
                        pfList = primeFactor.getPrimeFactor(val);
                        logger.debug(" adding PF to cache ");
                        pfCache.addPFsToCache(val, pfList);
                    } else {
                        logger.debug(" element available, using cache to get the Prime Factor");
                    }
                    writeOutput.printTheList(pfList, val);

                } else {
                    // can add to exceptions list for input line numbers which were not positive integers
                    // can be written to file
                }
            }
        }catch (IOException ioe){
            logger.error(ioe.getMessage());
            throw  new UDCheckedException("Error Reading the file, please try again ");
        }
    }



}
