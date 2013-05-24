package dat255.group5.chalmersonthego;

import com.example.chalmersonthego.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CalorieDialog extends Dialog {

	private ProgressBar beerProgressBar;
	private ProgressBar shotProgressBar;
	private ProgressBar wineProgressBar;
	private ProgressBar waterProgressBar;
	private ProgressBar ciderProgressBar;
	private TextView beerTextField;
	private TextView shotTextField;
	private TextView wineTextField;
	private TextView waterTextField;
	private TextView ciderTextField;
	private int amountOfBeers = 0;
	private int amountOfShots = 0;
	private int amountOfWine = 0;
	private int amountOfCider = 0;
	private int amountOfWater = 0;
	private final int STEPSINBEER = 2500;
	private final int STEPSINSHOT = 500;
	private final int STEPSINWINE = 1400;
	private final int STEPSINWATER = 10;
	private final int STEPSINCIDER = 2300;
	private int steps;

	/**
	 * Create the custom dialog interface with calories
	 * @param context the owning context
	 * @param step the amount of steps taken, from stepcounter
	 */
	public CalorieDialog(Context context, int step) {
		super(context);
		this.steps = step;
		setContentView(R.layout.calorie_progress);
		setTitle("Calorie progress!");

		// Beer realted code
		beerTextField = (TextView) super.findViewById(R.id.beerText);
		beerProgressBar = (ProgressBar) super
				.findViewById(R.id.beerProgressBar);
		beerProgressBar.setMax(STEPSINBEER + STEPSINBEER * amountOfBeers);

		// Shot related code
		shotTextField = (TextView) super.findViewById(R.id.shotText);
		shotProgressBar = (ProgressBar) super
				.findViewById(R.id.shotProgressBar);
		shotProgressBar.setMax(STEPSINSHOT + STEPSINSHOT * amountOfShots);

		// Wine related code
		wineTextField = (TextView) super.findViewById(R.id.wineText);
		wineProgressBar = (ProgressBar) super
				.findViewById(R.id.wineProgressBar);
		wineProgressBar.setMax(STEPSINWINE + STEPSINWINE * amountOfWine);

		// Cider related code
		ciderTextField = (TextView) super.findViewById(R.id.ciderText);
		ciderProgressBar = (ProgressBar) super
				.findViewById(R.id.ciderProgressBar);
		ciderProgressBar.setMax(STEPSINCIDER + STEPSINCIDER * amountOfCider);

		// Water related code
		waterTextField = (TextView) super.findViewById(R.id.waterText);
		waterProgressBar = (ProgressBar) super
				.findViewById(R.id.waterProgressBar);
		waterProgressBar.setMax(STEPSINWATER + STEPSINWATER * amountOfWater);
		
		// Add listeners to buttons
		setButtons();
		updateCalorieWindow();
	}
	
	/**
	 * Called to update the text in the dialog view if something is changed
	 */
	public void updateCalorieWindow() {
		wineTextField.setText(steps + "/"
				+ (STEPSINWINE + STEPSINWINE * amountOfWine)
				+ " Wine progress bar. Previous wineglasses: " + amountOfWine);
		beerTextField.setText(steps + "/"
				+ (STEPSINBEER + STEPSINBEER * amountOfBeers)
				+ " Beer progress bar. Previous beers: " + amountOfBeers);
		shotTextField.setText(steps + "/"
				+ (STEPSINSHOT + STEPSINSHOT * amountOfShots)
				+ " Shot progress bar. Previous shots: " + amountOfShots);
		ciderTextField.setText(steps + "/"
				+ (STEPSINCIDER + STEPSINCIDER * amountOfCider)
				+ " Cider progress bar. Previous ciders: " + amountOfCider);
		waterTextField.setText(steps + "/"
				+ (STEPSINWATER + STEPSINWATER * amountOfWater)
				+ " Water progress bar. Previous water: " + amountOfWater);
		beerProgressBar.setProgress(steps);
		wineProgressBar.setProgress(steps);
		shotProgressBar.setProgress(steps);
		ciderProgressBar.setProgress(steps);
		waterProgressBar.setProgress(steps);

	}

	/**
	 * Setting upp all the buttons and their listeners, aswell as onClick code
	 */
	private void setButtons() {
		Button beerButton = (Button) findViewById(R.id.beerDrinkButton);
		beerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (steps < STEPSINBEER * amountOfShots + STEPSINBEER) {
					Toast.makeText(getContext(),
							"You will get fat if you continue like this",
							Toast.LENGTH_SHORT).show();
				}
				// Set progress bar to next level of steps
				amountOfBeers++;
				beerProgressBar.setMax(STEPSINBEER + STEPSINBEER
						* amountOfBeers);
				updateCalorieWindow();
			}
		});

		Button waterButton = (Button) findViewById(R.id.waterDrinkButton);
		waterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (steps < STEPSINWATER * amountOfWater + STEPSINWATER) {
					Toast.makeText(getContext(),
							"You will get fat if you continue like this",
							Toast.LENGTH_SHORT).show();
				}
				// Set progress bar to next level of steps
				amountOfWater++;
				waterProgressBar.setMax(STEPSINWATER + STEPSINWATER
						* amountOfWater);
				updateCalorieWindow();
			}
		});

		Button ciderButton = (Button) findViewById(R.id.ciderDrinkButton);
		ciderButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (steps < STEPSINCIDER * amountOfCider + STEPSINCIDER) {
					Toast.makeText(getContext(),
							"You will get fat if you continue like this",
							Toast.LENGTH_SHORT).show();
				}
				// Set progress bar to next level of steps
				amountOfCider++;
				ciderProgressBar.setMax(STEPSINCIDER + STEPSINCIDER
						* amountOfCider);
				updateCalorieWindow();
			}
		});

		Button wineButton = (Button) findViewById(R.id.wineDrinkButton);
		wineButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (steps < STEPSINWINE * amountOfWine + STEPSINWINE) {
					Toast.makeText(getContext(),
							"You will get fat if you continue like this",
							Toast.LENGTH_SHORT).show();
				}
				// Set progress bar to next level of steps
				amountOfWine++;
				wineProgressBar
						.setMax(STEPSINWINE + STEPSINWINE * amountOfWine);
				updateCalorieWindow();
			}
		});
		Button shotButton = (Button) findViewById(R.id.shotDrinkButton);
		shotButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (steps < STEPSINSHOT * amountOfShots + STEPSINSHOT) {
					Toast.makeText(getContext(),
							"You will get fat if you continue like this",
							Toast.LENGTH_SHORT).show();
				}
				// Set progress bar to next level of steps
				amountOfShots++;
				shotProgressBar.setMax(STEPSINSHOT + STEPSINSHOT
						* amountOfShots);
				updateCalorieWindow();
			}
		});
	}

	/**
	 * Called to update the amount of steps, pushed to the stepcounter
	 * @param steps
	 */
	public void setSteps(int steps) {
		this.steps = steps + 1;
	}
}
