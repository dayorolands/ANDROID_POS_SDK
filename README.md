# SmartPOS SDK Technical Documentation
The SDK is divided into three(3) modules namely -
  - smart-pos-core
  - smart-pos-emv-pax
  - smart-pos-usb
  

To start using the SDK, follow the steps below:
- add module `smart-pos-core` and module `smart-pos-emv-pax` to your project. If you want to make use of USB communication between the SDK and a PC, add the third module `smart-pos-usb`
- Configure the SDK in your android `Application` class or your main `Activity` like below
```Java
// create POSDevice Implementation instance for pax device
POSDeviceImpl device  = POSDeviceImpl.create(getApplicationContext());

String clientId = "your-client-id";
String clientSecret = "your-client-secret";
String alias = "your-alias";
String merchantCode = "your-merchant-code";

// initialize POSConfig 
POSConfig config = new POSConfig(alias, clientId, clientSecret, merchantCode);

// setup terminal
IswPos.setupTerminal(getApplication(), device, config);
```

### Configure Terminal
In order to communicate effectively with the terminal, some initialization and configuration needs to be done, in your main activity, you can add a menu to achieve this:

```Java
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.terminal_config) {
            IswPos.showSettingsScreen(); // show settings for terminal configuration
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
```

### Initiating Payment
You can trigger SDK payment flow using the code below

```Java
int KOBO = 100;
int amount = 1000 * KOBO; // convert to kobo

// to start the payment flow, pass the following parameters to IswPos#initiatePayment()
// activity - the calling activity instance
// amount - the amount you want to accept in KOBO
// paymentType - the specific payment type you want to trigger
// there are four(4) payment types that you can use
// and they are (CARD, USSD, QR and PAYCODE), if paymentType is null
// A default screen will be displayed for you to choose the type of payment
    try {
        // trigger payment
        IswPos.getInstance().initiatePayment(this, currentAmount, null);
    } catch (NotConfiguredException e) {
        Toast.makeText(this, "Pos has not been configured", Toast.LENGTH_LONG).show();
    }
```

The SDK will take over from here. You can override your Activity's `onActivityResult` method to handle the result of the initiated payment

```Java
    @Override
    protected void onActivityResult(int requestCode,
    int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // handle success
            PurchaseResult result = IswPos.getResult(data);
            // process result here
            // you can access the following properties in PurchaseResult
            // class PurchaseResult {
            //     String responseCode = "";
            //     String responseMessage = "";
            //     String transactionReference = "";
            // }
        } else {
            // else handle error
        }
    }
```

##### Note on USB communication

For USB communication between the SDK and a PC to work, the below steps need to be followed

- ADB must be installed and added to Path enviroment variable on the PC
- USB Debugging must be enabled in the Android terminal