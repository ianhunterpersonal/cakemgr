import React from 'react';

import { Grid } from '@material-ui/core';

const cakes = [
    { "id": 6, "title": "Title 6", "description": "Description 6", "image": "Image 6" }
];

const CakeList = () => {

    return (
        <>
            {cakes.map(c => 
                <>
                    <Grid item key={c.id}>{c.id}</Grid>
                    <Grid item>{c.title}</Grid>
                    <Grid item>{c.description}</Grid>
                    <Grid item>{c.image}</Grid>
                </>
            )}

        </>
    )
    // <Container className={classes.container} maxWidth="xs">
    //     <form >
    //         <Grid container spacing={3}>
    //             <Grid item xs={12}>
    //                 <Grid container spacing={2}>
    //                     <Grid item xs={12}>
    //                         <TextField label="Title" onChange={titleInputChanged} value={titleEntered} />
    //                     </Grid>
    //                     <Grid item xs={12}>
    //                         <TextField label="Description" onChange={descrInputChanged} value={descrEntered} />
    //                     </Grid>
    //                     <Grid item xs={12}>
    //                         <TextField label="Image URL" onChange={imageUrlInputChanged} value={imageUrlEntered} />
    //                     </Grid>
    //                 </Grid>
    //             </Grid>
    //             <Grid item xs={12}>
    //                 <Button variant="contained" color="primary" onClick={onSubmit} >Save</Button>
    //             </Grid>
    //         </Grid>
    //     </form>
    // </Container>

}

export default CakeList;