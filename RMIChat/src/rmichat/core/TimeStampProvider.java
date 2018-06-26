/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.core;

import java.sql.Timestamp;

/**
 *
 * @author Krzysztof
 */
public class TimeStampProvider {
    public String getCurrent() {
        return "[" + new Timestamp(System.currentTimeMillis()) + "]";
    }
}
