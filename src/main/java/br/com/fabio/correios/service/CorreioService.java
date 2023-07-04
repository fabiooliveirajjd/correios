package br.com.fabio.correios.service;

import br.com.fabio.correios.CorreiosApplication;
import br.com.fabio.correios.entidade.Address;
import br.com.fabio.correios.entidade.AddressStatus;
import br.com.fabio.correios.entidade.Status;
import br.com.fabio.correios.exception.NoContentException;
import br.com.fabio.correios.exception.NotReadyException;
import br.com.fabio.correios.repository.AddressRepository;
import br.com.fabio.correios.repository.AddressStatusRepository;
import br.com.fabio.correios.repository.SetupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class CorreioService {

    private static Logger logger = LoggerFactory.getLogger(CorreioService.class);
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressStatusRepository statusRepository;

    @Autowired
    private SetupRepository setupRepository;

    @Value("${setup.on.startup}")
    private boolean setupOnStartup;

    @Value("${spring.datasource.url}")
    private String a;

 /*   public Status getStatus() {
        return this.addressStatusRepository.findById(AddressStatus.DEFAULT_ID)
                .orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
                .getStatus();
    }

    public Address getAddressByZipcode(String zipcode) throws NoContentException, NotReadyException {
        if (this.getStatus().equals(Status.READY))
            throw new NotReadyException();

        return addressRepository.findById(zipcode)
                .orElseThrow(NoContentException::new);
    }

    private void saveStatus(Status status) {
        this.addressStatusRepository.save(AddressStatus.builder()
                .id(AddressStatus.DEFAULT_ID)
                .status(status)
                .build());
    }

    @Async
    @EventListener(ApplicationStartedEvent.class)
    protected void setupOnStartup() {
        try {
            this.setup();
        } catch (Exception exc) {
            CorreiosApplication.close(999);
        }
    }

    public void setup() throws Exception {

        if (this.getStatus().equals(Status.NEED_SETUP)) {
            this.saveStatus(Status.SETUP_RUNNING);
            try {
                this.addressRepository.saveAll(this.setupRepository.getFromOrigin());
            } catch (Exception exc) {
                this.saveStatus(Status.NEED_SETUP);
                throw exc;
            }

            this.saveStatus(Status.READY);

        }
    }
}*/

    public Status getStatus() {
        return this.statusRepository.findById(AddressStatus.DEFAULT_ID)
                .orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
                .getStatus();
    }

    public Address getByZipcode(String zipcode) throws NotReadyException, NoContentException {
        if (!this.getStatus().equals(Status.READY))
            throw new NotReadyException();

        return addressRepository.findById(zipcode).orElseThrow(NoContentException::new);
    }

    private void saveServiceStatus(Status status) {
        statusRepository.save(AddressStatus.builder().id(AddressStatus.DEFAULT_ID).status(status).build());
    }

    @Async
    @EventListener(ApplicationStartedEvent.class)
    protected void setupOnStartup() {
        if (setupOnStartup) {
            this.setup();
        }
    }

    public synchronized void setup() {
        logger.info("---" + a);
        logger.info("---");
        logger.info("--- STARTING SETUP");
        logger.info("--- Please wait... This may take a few minutes");
        logger.info("---");
        logger.info("---");

        try {
            if (this.getStatus().equals(Status.NEED_SETUP)) { // If not running, starts it.
                this.saveServiceStatus(Status.SETUP_RUNNING);

                //
                // Download CSV content
                // From origin and saves it.
                this.addressRepository.saveAll(
                        setupRepository.getFromOrigin());

                //
                // Set service READY!
                this.saveServiceStatus(Status.READY);
            }

            logger.info("---");
            logger.info("---");
            logger.info("--- READY TO USE");
            logger.info("--- Good luck my friend :)");
            logger.info("---");
            logger.info("---");
        } catch (Exception exc) {
            logger.error("Error to download/save addresses, closing the application....", exc);
            CorreiosApplication.close(999);
        }
    }
}
