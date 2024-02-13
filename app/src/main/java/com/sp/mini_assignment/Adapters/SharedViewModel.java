package com.sp.mini_assignment.Adapters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sp.mini_assignment.Adapters.CardDetails;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<CardDetails> cardDetailsLiveData = new MutableLiveData<>();

    // Getter method for observing changes in the card details
    public LiveData<CardDetails> getCardDetails() {
        return cardDetailsLiveData;
    }

    // Setter method to update the card details
    public void setCardDetails(CardDetails cardDetails) {
        cardDetailsLiveData.setValue(cardDetails);
    }
}
