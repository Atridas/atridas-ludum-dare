using UnityEngine;
using System.Collections;

public class FinishScript : MonoBehaviour {

	public AudioSource audio;
	public AudioClip finalClip;

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void OnTriggerEnter2D(Collider2D collider) {
		
		PlayerScript player = collider.GetComponent<PlayerScript> ();
		if (player != null) {
			player.gameObject.SetActive(false);
			GetComponent<Animator>().SetTrigger ("FinalAnimation");
			GameObject.FindGameObjectWithTag("MainCamera").GetComponent<Camera>().enabled = false;
			audio.Stop();
			audio.clip = finalClip;
			audio.Play();
		}
	}

	void onAnimFinish() {
		
		AllDataScript allDataScript = GameObject.FindGameObjectWithTag ("AllData").GetComponent<AllDataScript> ();
		allDataScript.isEnd = true;
		allDataScript.isStart = false;
		Application.LoadLevel (0);

	}
}
