using UnityEngine;
using System.Collections;

public class EnemyScript : MonoBehaviour {

	public int skin = 1;
	public int lives = 3;
	public float minDisparTime = 1, maxDisparTime = 2;
	public float disparSpeed = 10;
	public Transform spawner;
	public Rigidbody2D bulletPrefab;
	
	private Animator anim;

	private bool onHitAnimation;
	private float disparTime;

	// Use this for initialization
	void Start () {
		anim = GetComponent<Animator>();
		anim.SetTrigger ("skin" + skin);

		onHitAnimation = false;

		disparTime = Random.Range (minDisparTime, maxDisparTime);
	}
	
	// Update is called once per frame
	void Update () {
		if(!onHitAnimation) {
			disparTime -= Time.deltaTime;
			if (disparTime <= 0) {
				disparTime += Random.Range (minDisparTime, maxDisparTime);
				
				Rigidbody2D bulletInstance = Instantiate(bulletPrefab, spawner.position, Quaternion.Euler(new Vector3(0,0,0))) as Rigidbody2D;
				bulletInstance.velocity = new Vector3(disparSpeed,0,0);
			}
		}
	}

	void OnTriggerEnter2D(Collider2D collider) {
		if (collider.gameObject.tag.Equals ("Bala")) {
			if(!onHitAnimation) {
				lives--;
				if(lives > 0) {
					onHitAnimation = true;
					anim.SetTrigger ("hit");
				} else {
					anim.SetTrigger("die");
				}
			}
			Destroy(collider.gameObject);
		}
	}

	public void onDie()
	{
		Destroy(gameObject);
	}
	
	public void endHit()
	{
		anim.SetTrigger ("endhit");
		onHitAnimation = false;
	}
}
