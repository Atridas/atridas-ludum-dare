using UnityEngine;
using System.Collections;

public class EnemyScript : MonoBehaviour {

	public int skin;
	
	private Animator anim;

	private bool onHitAnimation;

	// Use this for initialization
	void Start () {
		anim = GetComponent<Animator>();
		anim.SetTrigger ("skin" + skin);

		onHitAnimation = false;
	}
	
	// Update is called once per frame
	void Update () {

	}

	void OnTriggerEnter2D(Collider2D collider) {
		if (collider.gameObject.tag.Equals ("Bala")) {

		}
	}
	
	public void startHit()
	{
		
	}
	
	public void endHit()
	{
		
	}
}
