package com.twu.biblioteca;

import com.twu.biblioteca.representations.Rating;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class RatingTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldIllegalArgumentExceptionIfRatingCreatedIsLessThan1() {
        exception.expect(IllegalArgumentException.class);
        Rating rating = new Rating(0);
    }

    @Test
    public void shouldIllegalArgumentExceptionIfRatingCreatedIsGreaterThan10() {
        exception.expect(IllegalArgumentException.class);
        Rating rating = new Rating(11);
    }

    @Test
    public void shouldIllegalArgumentExceptionIfRatingIsSetToAValueLessThan1() {
        Rating rating = new Rating();
        exception.expect(IllegalArgumentException.class);
        rating.setValue(0);
    }

    @Test
    public void shouldIllegalArgumentExceptionIfRatingIsSetToAValueGreaterThan10() {
        Rating rating = new Rating();
        exception.expect(IllegalArgumentException.class);
        rating.setValue(11);
    }

    @Test
    public void shouldSuccessfulyPrintValueOf1IfRatingIsSetToAValueOf1() {
        Rating rating = new Rating(1);
        assertThat(rating.toString(), is("Rating: 1"));
    }

    @Test
    public void shouldSuccessfulyPrintValueOf1IfRatingIsCreatedWithValue1() {
        Rating rating = new Rating(1);
        assertThat(rating.toString(), is("Rating: 1"));
    }

    @Test
    public void shouldSuccessfulyPrintValueOf10IfRatingIsCreatedWithValue10() {
        Rating rating = new Rating(10);
        assertThat(rating.toString(), is("Rating: 10"));
    }

    @Test
    public void shouldSuccessfulyPrintValueOf7IfRatingIsCreatedWithValue7() {
        Rating rating = new Rating(7);
        assertThat(rating.toString(), is("Rating: 7"));
    }


}
