using UnityEngine;
using System.Collections;

public class GUIScript : MonoBehaviour {

	public Camera cam;
	
	public GameObject background, button1, button2, button3;

	public Sprite titleScreen, credits, endScreen;

	private AllDataScript allDataScript;

	// Use this for initialization
	void Start () {

		allDataScript = GameObject.FindGameObjectWithTag ("AllData").GetComponent<AllDataScript> ();
	}
	
	// Update is called once per frame
	void Update () {
		button1.SetActive (allDataScript.isStart);
		button2.SetActive (allDataScript.isStart); 
		button3.SetActive (allDataScript.isStart);

		if(allDataScript.isStart) {
			background.GetComponent<SpriteRenderer>().sprite = titleScreen;
		} else if(allDataScript.isCredits) {
			background.GetComponent<SpriteRenderer>().sprite = credits;
		} else if(allDataScript.isEnd) {
			background.GetComponent<SpriteRenderer>().sprite = endScreen;
		}

		if (Input.GetMouseButtonDown (0)) {
			
			Vector3 mousePosition = cam.ScreenToWorldPoint(Input.mousePosition);
			Vector3 mousePosition2 = cam.ScreenToWorldPoint(Input.mousePosition + new Vector3(100,1,0));

			//Physics2D.Linecast(Camera.ScreenToWorldPoint(Input.mousePosition)
			RaycastHit2D raycast = Physics2D.Linecast(mousePosition, mousePosition2);

			bool playPressed, creditsPressed, exitPressed;
			playPressed = creditsPressed = exitPressed = false;
			if(raycast != null && raycast.collider != null) {
				if(raycast.collider.gameObject == button1) {
					playPressed = true;
				}
				if(raycast.collider.gameObject == button2) {
					creditsPressed = true;
				}
				if(raycast.collider.gameObject == button3) {
					exitPressed = true;
				}
			}

			if(playPressed) {
				Application.LoadLevel (1);
			} else if(creditsPressed) {
				allDataScript.isStart = false;
				allDataScript.isCredits = true;
			} else if(exitPressed) {
				Application.Quit ();
			} else if(allDataScript.isCredits) {
				allDataScript.isStart = true;
				allDataScript.isCredits = false;
			} else if(allDataScript.isEnd) {
				allDataScript.isEnd = false;
				allDataScript.isCredits = true;
			}

		}
	}

}
