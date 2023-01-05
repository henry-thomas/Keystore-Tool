/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.henry.keystoretool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author henry
 */
public class KeyStoreTool {

    private static final Logger LOG = Logger.getLogger(KeyStoreTool.class.getName());

    public static void main(String[] args) {
        String path;
        String password = "changeit";
        if (args.length > 0) {
            path = args[0];
        } else {
            LOG.severe("Please pass path to keystore as first argument!");
            return;
        }
        if (args.length >= 2) {
            password = args[1].toString();
        } else {
            LOG.severe("No Password provided, using 'changit'");
        }

        try {

            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(new FileInputStream(new File(path)), password.toCharArray());

            Enumeration<String> aliases = ks.aliases();

            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();

                ks.deleteEntry(alias);
                LOG.log(Level.INFO, "Removing: {0}", alias);
            }

            ks.store(new FileOutputStream(new File(path)), password.toCharArray());
            LOG.info("Done!");
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) {
            Logger.getLogger(KeyStoreTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
