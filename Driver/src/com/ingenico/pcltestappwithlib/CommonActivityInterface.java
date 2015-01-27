package com.ingenico.pcltestappwithlib;

abstract interface CommonActivityInterface 
{
	abstract void onBarCodeReceived(String barCodeValue);
	abstract void onStateChanged(String state);
}
