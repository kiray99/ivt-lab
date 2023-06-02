package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;

  @BeforeEach
  public void init() {
    mockPrimary = mock(TorpedoStore.class);
    mockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimary.fire(1))
      .thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.fire(1))
    .thenReturn(true);
    when(mockSecondary.fire(1))
      .thenReturn(true);
    

  // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, result);

  }

  @Test
  public void fireTorpedo_Single_AllEmpty(){
    // Arrange
    when(mockPrimary.isEmpty())
    .thenReturn(true);
    when(mockSecondary.isEmpty())
      .thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(0)).fire(1);
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
    assertEquals(false, result);

  }

  @Test
  public void fireTorpedo_Single_FirstEmpty_FireSecondary(){
    // Arrange
    when(mockPrimary.isEmpty())
    .thenReturn(true);
    
    when(mockSecondary.isEmpty())
      .thenReturn(false);
    when(mockSecondary.fire(1))
    .thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
    assertEquals(true, result);

  }
}
