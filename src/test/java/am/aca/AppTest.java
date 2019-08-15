package am.aca;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringApplication;

import javax.validation.constraints.AssertTrue;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class AppTest {
    @Mock
    SpringApplication springApplication;



    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
