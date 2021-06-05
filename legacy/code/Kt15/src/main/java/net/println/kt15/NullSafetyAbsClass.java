package net.println.kt15;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by benny on 12/17/16.
 */
public abstract class NullSafetyAbsClass {
    public abstract String formatDate(Date date);

    public @NotNull String formatTime(@NotNull Date date){
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }
}
