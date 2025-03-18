document.addEventListener('DOMContentLoaded', function() {
    // Egg counter and shake effect
    const egg = document.querySelector('img[alt="Egg"]');
    let counter = 0;
    const counterDisplay = document.getElementById('egg-counter');

    egg.addEventListener('click', function() {
        counter++;
        counterDisplay.textContent = `Clicks: ${counter}`;
        egg.classList.add('shake');
        setTimeout(() => egg.classList.remove('shake'), 500);
    });

    // Jumping effects
    const character = document.getElementById('character');
    const jumpButton = document.getElementById('jump-button');

    jumpButton.addEventListener('click', function() {
        if (!character.classList.contains('jump')) {
            character.classList.add('jump');
            setTimeout(function() {
                character.classList.remove('jump');
            }, 500); 
        }
    });
});
// List toggle
document.addEventListener('DOMContentLoaded', function() {
    const randomButton = document.getElementById('random-button');
    const randomList = document.getElementById('random-list');
    const randomItems = ["Apple", "Banana", "Cherry", "Date", "Elderberry"];
    let isListDisplayed = false; // Boolean flag to track display status

    randomButton.addEventListener('click', function() {
        if (!isListDisplayed) {
            // Display the list
            randomList.innerHTML = ''; // Clear existing list items
            randomItems.forEach(function(item) {
                let li = document.createElement('li');
                li.textContent = item;
                randomList.appendChild(li);
            });
        } else {
            // Hide the list
            randomList.innerHTML = '';
        }
        isListDisplayed = !isListDisplayed; // Toggle the display status
    });
});



// Paragraph toggle
document.addEventListener('DOMContentLoaded', function() {
    const clickButton = document.getElementById('click-button');
    const paragraph = document.getElementById('paragraph');

    clickButton.addEventListener('click', function() {
        // Toggle visibility of the paragraph
        if (paragraph.style.display === 'none' || paragraph.style.display === '') {
            paragraph.style.display = 'block';
        } else {
            paragraph.style.display = 'none';
        }
    });
});


