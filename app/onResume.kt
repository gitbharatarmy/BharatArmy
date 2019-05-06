override fun onResume() {
    favoriteButton.setOnClickListener { morphView.showAvdFirst() }
    toggleButton.setOnClickListener { morphView.morph() }
    sendButton.setOnClickListener { morphView.showAvdSecond() }
}