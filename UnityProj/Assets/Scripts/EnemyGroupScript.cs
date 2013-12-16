using UnityEngine;
using System.Collections;

public class EnemyGroupScript : MonoBehaviour {

	public bool startActive;

	// Use this for initialization
	void Start () {
		gameObject.SetActive (startActive);
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
