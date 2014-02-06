/*
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * A representation of a day-of-month in the ISO-8601 calendar system.
 * <p>
 * DayOfMonth is an immutable time field that can only store a day-of-month.
 * It is a type-safe way of representing a day-of-month in an application.
 * <p>
 * Static factory methods allow you to construct instances.
 * The day-of-month may be queried using getValue().
 *
 * @implSpec
 * This class is immutable and thread-safe.
 * <p>
 * This class must be treated as a value type. Do not synchronize, rely on the
 * identity hash code or use the distinction between equals() and ==.
 */
public final class DayOfMonth
        implements Comparable<DayOfMonth>, TemporalAdjuster, Serializable {

    /**
     * A serialization identifier for this instance.
     */
    private static final long serialVersionUID = -8840172642009917873L;
    /**
     * Cache of singleton instances.
     */
    private static final AtomicReferenceArray<DayOfMonth> CACHE = new AtomicReferenceArray<DayOfMonth>(31);

    /**
     * The day-of-month being represented, from 1 to 31.
     */
    private final int dayOfMonth;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code DayOfMonth}.
     * <p>
     * A day-of-month object represents one of the 31 days of the month, from
     * 1 to 31.
     *
     * @param dayOfMonth  the day-of-month to represent, from 1 to 31
     * @return the day-of-month, not null
     * @throws DateTimeException if the day-of-month is invalid
     */
    public static DayOfMonth of(int dayOfMonth) {
        try {
            DayOfMonth result = CACHE.get(--dayOfMonth);
            if (result == null) {
                DayOfMonth temp = new DayOfMonth(dayOfMonth + 1);
                CACHE.compareAndSet(dayOfMonth, null, temp);
                result = CACHE.get(dayOfMonth);
            }
            return result;
        } catch (IndexOutOfBoundsException ex) {
            throw new DateTimeException("Invalid value for DayOfYear: " + ++dayOfMonth);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code DayOfMonth} from a date-time object.
     * <p>
     * A {@code TemporalAccessor} represents some form of date and time information.
     * A {@code TemporalAccessor} represents some form of date and time information.
     * This factory converts the arbitrary date-time object to an instance of {@code DayOfMonth}.
     *
     * @param dateTime  the date-time object to convert, not null
     * @return the day-of-month, not null
     * @throws DateTimeException if unable to convert to a {@code DayOfMonth}
     */
    public static DayOfMonth from(TemporalAccessor dateTime) {
        LocalDate date = LocalDate.from(dateTime);
        return DayOfMonth.of(date.getDayOfMonth());
    }

    //-----------------------------------------------------------------------
    /**
     * Constructs an instance with the specified day-of-month.
     *
     * @param dayOfMonth  the day-of-month to represent
     */
    private DayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Resolve the singleton.
     *
     * @return the singleton, never null
     */
    private Object readResolve() {
        return of(dayOfMonth);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the field that defines how the day-of-month field operates.
     * <p>
     * The field provides access to the minimum and maximum values, and a
     * generic way to access values within a date-time.
     *
     * @return the day-of-month field, never null
     */
    public TemporalField getField() {
        return ChronoField.DAY_OF_MONTH;
    }

    /**
     * Gets the day-of-month value.
     *
     * @return the day-of-month, from 1 to 31
     */
    public int getValue() {
        return dayOfMonth;
    }

    //-----------------------------------------------------------------------
    /**
     * Adjusts a date to have the value of this day-of-month, returning a new date.
     * <p>
     * If the day-of-month is invalid for the year and month then it will be changed
     * to the last valid date for the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param temporal  the temporal to be adjusted, not null
     * @return the adjusted date, never null
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(DAY_OF_MONTH, dayOfMonth);
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this day-of-month instance to another.
     *
     * @param otherDayOfMonth  the other day-of-month instance, not null
     * @return the comparator value, negative if less, positive if greater
     */
    public int compareTo(DayOfMonth otherDayOfMonth) {
        return Integer.compare(dayOfMonth, otherDayOfMonth.dayOfMonth);
    }

    //-----------------------------------------------------------------------
    /**
     * Is this instance equal to that specified, evaluating the day-of-month.
     *
     * @param otherDayOfMonth  the other day-of-month instance, null returns false
     * @return true if the day-of-month is the same
     */
    @Override
    public boolean equals(Object otherDayOfMonth) {
        return this == otherDayOfMonth;
    }

    /**
     * A hash code for the day-of-month object.
     *
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return dayOfMonth;
    }

    /**
     * A string describing the day-of-month object.
     *
     * @return a string describing this object
     */
    @Override
    public String toString() {
        return "DayOfMonth=" + getValue();
    }

}
