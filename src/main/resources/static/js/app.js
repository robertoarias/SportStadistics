var example1 = document.getElementById('example1'),
	example2Left = document.getElementById('example2-left'),
	example2Right = document.getElementById('example2-right'),
	example3Left = document.getElementById('example3-left'),
	example3Right = document.getElementById('example3-right'),
	gridDemo = document.getElementById('gridDemo');

// Example 1 - Simple list
new Sortable(example1, {
	animation: 150,
	ghostClass: 'blue-background-class'
});


// Example 2 - Shared lists
new Sortable(example2Left, {
	group: 'shared', // set both lists to same group
	animation: 150
});

new Sortable(example2Right, {
	group: 'shared',
	animation: 150
});

// Example 3 - Cloning
new Sortable(example3Left, {
	group: {
		name: 'shared',
		pull: 'clone' // To clone: set pull to 'clone'
	},
	animation: 150
});

new Sortable(example3Right, {
	group: {
		name: 'shared',
		pull: 'clone'
	},
	animation: 150
});


// Grid demo
new Sortable(gridDemo, {
	animation: 150,
	ghostClass: 'blue-background-class'
});

// Nested demo
var nestedSortables = [].slice.call(document.querySelectorAll('.nested-sortable'));

// Loop through each nested sortable element
for (var i = 0; i < nestedSortables.length; i++) {
	new Sortable(nestedSortables[i], {
		group: 'nested',
		animation: 150,
		fallbackOnBody: true,
		swapThreshold: 0.65
	});
}

