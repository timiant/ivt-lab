package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryStore;
  private TorpedoStore mockSecondaryStore;

  @BeforeEach
  public void init(){
    this.mockPrimaryStore = mock(TorpedoStore.class);
    this.mockSecondaryStore = mock(TorpedoStore.class);

    this.ship = new GT4500(mockPrimaryStore, mockSecondaryStore);
  }

  @Test
  public void fireTorpedo_Single_OnlyPrimaryStore_Success(){
    // Arrange
    when(mockPrimaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    // Verifying the mock: fire was called only once
    verify(mockPrimaryStore, times(1)).fire(1);
    assertEquals(true, result);
  }

  
  @Test
  public void fireTorpedo_Single_SecondaryStoreTest(){
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(false);
    
    when(mockSecondaryStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    // Verifying the mock
    verify(mockPrimaryStore, times(0)).fire(1);
    verify(mockSecondaryStore, times(1)).fire(1);
  }


  @Test
  public void fireTorpedo_Single_PrimaryStoreTest(){
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);
    
    when(mockPrimaryStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    // Verifying the mock
    verify(mockPrimaryStore, times(1)).fire(1);
    verify(mockSecondaryStore, times(0)).fire(1);
  }


  @Test
  public void fireTorpedo_Single_BothStoresAreEmpty(){
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    // Verifying the mock: fire was called only once in each case
    verify(mockPrimaryStore, times(0)).fire(1);
    verify(mockSecondaryStore, times(0)).fire(1);
  }


  @Test
  public void fireTorpedo_Single_WasPrimaryStoreFiredAlready_SecondaryStoreTest(){
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(false);
    
    when(mockSecondaryStore.fire(1)).thenReturn(true);

    // Act
    ship.setPrimaryFiredLast(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    // Verifying the mock: fire was called only once in each case
    verify(mockPrimaryStore, times(0)).fire(1);
    verify(mockSecondaryStore, times(1)).fire(1);
  }


  @Test
  public void fireTorpedo_Single_WasPrimaryStoreFiredAlready_PrimaryStoreTest(){
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);
    
    when(mockPrimaryStore.fire(1)).thenReturn(true);

    // Act
    ship.setPrimaryFiredLast(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    // Verifying the mock: fire was called only once in each case
    verify(mockPrimaryStore, times(1)).fire(1);
    verify(mockSecondaryStore, times(0)).fire(1);
  }


  @Test
  public void fireTorpedo_Single_StartsWithPrimaryStoreFired_BothStoresAreEmpty(){
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);

    // Act
    ship.setPrimaryFiredLast(true);
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    // Verifying the mock: fire was called only once in each case
    verify(mockPrimaryStore, times(0)).fire(1);
    verify(mockSecondaryStore, times(0)).fire(1);
  }
  
  @Test
  public void fireTorpedo_All(){
    // Arrange
    when(mockPrimaryStore.getTorpedoCount()).thenReturn(10);
    when(mockSecondaryStore.getTorpedoCount()).thenReturn(10);

    when(mockPrimaryStore.fire(anyInt())).thenReturn(true);
    when(mockSecondaryStore.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    // Verifying the mock: fire was called 10 times  with parameter 10 in primaryStore
    verify(mockPrimaryStore, times(1)).fire(10);
    // Verifying the mock: fire was called one times with parameter 10 in secondaryStore
    verify(mockSecondaryStore, times(1)).fire(10);

    assertEquals(true, result);
  }


  
}
