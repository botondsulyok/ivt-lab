package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  TorpedoStore primaryTorpedoStoreMock = mock(TorpedoStore.class);
  TorpedoStore secondaryTorpedoStoreMock = mock(TorpedoStore.class);

  @BeforeEach
  public void init(){
    this.ship = new GT4500(primaryTorpedoStoreMock, secondaryTorpedoStoreMock);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_PrimaryEmpty_SecondaryNotEmpty_Success() {
    // Arrange
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStoreMock, times(0)).fire(1);
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Failed() {
    // Arrange
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(false);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_SingleTwice_Success() {
    // Arrange
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_PrimaryEmpty_Failed() {
    // Arrange
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(false);
    when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStoreMock, times(0)).fire(1);
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_PrimarySuccess_SecondaryFailed() {
    // Arrange
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_WithSecondary_Empty_SoFiresWithPrimary_Success() {
    // Arrange
    when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStoreMock, times(2)).fire(1);
    verify(secondaryTorpedoStoreMock, times(0)).fire(1);
  }




}
